package com.nafepay.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nafepay.core.di.Preferences
import com.nafepay.domain.database.daos.UserDao
import com.nafepay.domain.database.models.User
import com.nafepay.main.events.MainEvent
import com.nafepay.main.states.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch




class MainViewModel(
    private val sharedPreferences: Preferences,
    private val userDao: UserDao
) :  ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    val uiState: StateFlow<MainState> = _uiState

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()


    init {

             viewModelScope.launch {

                 sharedPreferences.isVerified?.collectLatest { isVer ->
                     _uiState.value = uiState.value.build {
                         if(isVer != null)
                             isVerified = isVer
                     }
                 }
                 sharedPreferences.accessToken?.let { token ->
                     token.collectLatest{ it ->
                         if(it != null && it != "") {

                             userDao.getUser().collectLatest{ usr ->
                                 _uiState.value = uiState.value.build {
                                     isAuthenticated = true
                                     user = usr
                                 }
                             }

                         }
                         else{
                             _uiState.value = uiState.value.build {
                                 isAuthenticated = false
                             }
                         }
                     }
               }
           }
    }
    fun handleMainEvent(event : MainEvent){
        _uiState.value = uiState.value.build {
            when (event) {


                is MainEvent.SearchTextChanged -> {
                    search = event.search
                }
                is MainEvent.LoadLocation  -> {
                    location =  event.location
                }

                else -> {

                }
            }
        }
    }




}

