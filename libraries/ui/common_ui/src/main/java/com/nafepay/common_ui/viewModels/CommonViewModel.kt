package com.nafepay.common_ui.viewModels

import android.app.Application
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.*
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.utils.helpers.Utilities
import com.nafepay.core.di.Preferences
import com.nafepay.domain.database.daos.UserDao
import com.nafepay.domain.database.models.User
import com.nafepay.navigation.GeneralDirections
import com.nafepay.navigation.HomeDirections
import com.nafepay.navigation.NavigationManager
import com.nafepay.navigation.Screen
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nafepay.common_ui.utils.constants.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*


class CommonViewModel(
    application: Application,
    private val navigationManager: NavigationManager,
    private val sharedPreferences: Preferences,
    private val userDao: UserDao
) :  AndroidViewModel(application) {




    private val _uiState = MutableStateFlow(CommonState())
    val uiState: StateFlow<CommonState> = _uiState

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()

    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    val permissionsInitiallyRequestedState = MutableStateFlow(false)

    init{
        _uiState.value.build {
            //topBarColor = MaterialTheme.colors.onSurface
            //topBarTintColor = MaterialTheme.colors.surface
        }
    }


    override fun onCleared() {
        cameraExecutor.shutdown()
        super.onCleared()
    }

    init {

        viewModelScope.launch {
                 sharedPreferences.isVerified?.collectLatest { isVer ->
                     _uiState.value = uiState.value.build {
                         if(isVer != null)
                             isVerified = isVer
                     }


                 }

                 sharedPreferences.accessToken?.let { token ->
                     token.collectLatest { it ->
                         if(it != null && it != "") {

                             userDao.getUser().collect { usr ->
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
               sharedPreferences.encryptionKey.let { key ->
                   key.collectLatest { k ->
                       _uiState.value = uiState.value.build {

                       }
                   }

               }
           }

    }
    fun handleCommonEvent(event : CommonEvent){
        _uiState.value = uiState.value.build {
            when (event) {
                CommonEvent.DismissErrorDialog -> {

                }
                is CommonEvent.ChangeUserId ->{
                    userId = event.userId
                }

                is CommonEvent.ChangeBottomSheetAction -> {
                    bottomSheetAction = event.action
                }
                is CommonEvent.ChangeShowAddToPlaylist -> {
                    showAddToPlaylist = event.show
                }
                is CommonEvent.ChangeShowAddPlaylist -> {
                    showAddPlaylist = event.show
                }
                is CommonEvent.ChangeBottomSheetHeader -> {
                    bottomSheetHeader = event.header
                }
                is CommonEvent.ChangeLoading -> {
                    loading = event.loading
                }
                 CommonEvent.DismissToast -> {
                     this.toast = null
                 }
                CommonEvent.DoSearch -> {
                    doSearch()
                }


                is CommonEvent.ChangeShowMoreOptions -> {
                    showMoreOptions = event.show
                }
                is CommonEvent.ChangeShowSearchOption -> {
                    showSearchOption = event.show
                }

                is CommonEvent.ChangeShowSearchBar -> {
                    showSearchBar = event.show
                }
                is  CommonEvent.ChangeImagePreview ->{
                    previewUri = event.previewUri
                }
                is CommonEvent.ChangeWebViewUrl -> {
                    webUrl = event.uri
                }

                is CommonEvent.HasSearched -> {
                    hasSearchResult = event.searched
                }
                is CommonEvent.SearchTextChanged -> {
                    search = event.search
                }
                is CommonEvent.ShowToast -> {
                    this.toast = event.message
                }
                is CommonEvent.ToggleShowStory -> {
                    this.isShowingStory = event.show
                }
                CommonEvent.OnRefresh -> {
                    this.isRefreshing = true
                }
                CommonEvent.OnEndRefresh -> {
                    this.isRefreshing = false
                }
                is CommonEvent.GetBottomSheetOptions -> {
                    bottomSheetOptions = event.options
                }
                CommonEvent.NotificationsViewed -> {
                    this.numNotifications = 0
                }
                is CommonEvent.ChangeHasDeepScreen -> {
                    this.hasDeepScreen = event.isDeepScreen
                    this.screenTitle = event.deepScreenTitle

                }
                is CommonEvent.ChangeHasTopBar -> {
                    this.hasTopBar = event.showTopBar
                }
                is CommonEvent.ChangeHasBottomBar -> {
                    this.hasBottomBar = event.showBottomBar

                }
                is CommonEvent.ChangeTab -> {
                    if(currentTab != event.tab)
                       this.currentTab = event.tab
                }
                is CommonEvent.LoadUser -> {
                    user = event.user
                }

                CommonEvent.NavigateToNotifications ->{
                    navigationManager.navigate(HomeDirections.notifications)
                }
                is CommonEvent.ChangeBackPageData -> {
                       backPageData = event.data
                }

                is CommonEvent.ChangeTopBarColor -> {
                    topBarColor = event.color
                }
                is CommonEvent.ChangeTopBarTintColor -> {
                    topBarTintColor = event.color
                }
                CommonEvent.NavigateToSearch -> {
                    navigationManager.navigate(Screen.Search.route)
                }
                CommonEvent.NavigateUp -> {
                    navigationManager.navigate(GeneralDirections.back)
                }
                else -> {

                }
            }
        }
    }


    private fun doSearch() {
        viewModelScope.launch {
            val search = _uiState.value.search
        }
    }



}