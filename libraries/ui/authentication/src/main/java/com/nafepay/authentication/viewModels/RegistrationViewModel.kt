package com.nafepay.authentication.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nafepay.authentication.events.RegistrationEvent
import com.nafepay.authentication.states.RegistrationState
import com.nafepay.core.di.Preferences
import com.nafepay.core.utils.Validation
import com.nafepay.domain.database.daos.UserDao
import com.nafepay.domain.database.models.User
import com.nafepay.domain.exceptions.ServerException
import com.nafepay.domain.interactors.authentication.Authenticate
import com.nafepay.domain.models.authentication.registration.SignupResult
import com.nafepay.navigation.AuthenticationDirections
import com.nafepay.navigation.HomeDirections
import com.nafepay.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class RegistrationViewModel(
    private val authenticate: Authenticate,
    private val sharedPrefs: Preferences,
    private val userDao: UserDao,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState: StateFlow<RegistrationState> = _uiState

    private var _toast: MutableStateFlow<String> = MutableStateFlow("")
    val toast: StateFlow<String> get() = _toast

    val users: LiveData<List<User>> =  userDao.getUsers().asLiveData()
    var localUser: User? = null
    init {
        viewModelScope.launch {
             userDao.getUsers().collectLatest { users ->
                 localUser = users.firstOrNull()
                 if(localUser != null) {
                     Log.d("UserFound", localUser!!.id)
                     _uiState.value = uiState.value.build {
                         user = user
                     }
                 }
             }


        }
    }

    fun handleRegistrationEvent(event: RegistrationEvent) {
        if (event is RegistrationEvent.RegistrationClicked) {
            _uiState.value = uiState.value.build {
                loading = true
            }
            if(_uiState.value.isRegistrationContentValid) {
                register()
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Please  fill all fields"
                }
            }
        }
        else {
            _uiState.value = uiState.value.build {
                when (event) {

                    RegistrationEvent.DismissErrorDialog -> {
                        this.error = null
                    }
                    RegistrationEvent.RegistrationNavigationClicked ->{
                        navigationManager.navigate(AuthenticationDirections.registration)
                    }
                    RegistrationEvent.RegistrationClicked -> {
                        this.loading = true
                    }

                    RegistrationEvent.LoginClicked -> {
                        navigationManager.navigate(
                            AuthenticationDirections.login
                        )
                    }
                    RegistrationEvent.SocialRegisterClicked -> {
                        Log.d("Login", "Login clicked")
                        socialLogin()
                    }
                    RegistrationEvent.VerifyCodeClicked -> {

                        loading = true
                        if(isVerifyCodeContentValid) {
                            submitVerificationCode {
                                verificationDone = true
                            }
                        }
                        else{
                            loading = false
                        }
                    }
                    is RegistrationEvent.EmailChanged -> {

                            this.email = event.email
                            this.isEmailValid =
                                Validation.isEmail(event.email) && event.email.isNotEmpty()


                    }
                    is RegistrationEvent.PasswordChanged -> {
                        this.password = event.password
                    }
                    is RegistrationEvent.FullNameChanged -> {
                        this.fullname = event.fullname
                    }
                    is RegistrationEvent.ProfileUrlChanged -> {
                        profileImageUrl = event.url
                    }
                    is RegistrationEvent.SocialMediaServiceChanged-> {
                        this.socialMediaService = event.service
                    }
                    is RegistrationEvent.SocialMediaTokenChanged-> {
                        this.socialMediaToken = event.token
                    }
                    is RegistrationEvent.SocialMediaIsFirstTimeChanged-> {
                        this.socialMediaFirstTime = event.isFirstTime
                    }

                    is RegistrationEvent.UsernameChanged -> {
                        this.username = event.username

                    }
                    is RegistrationEvent.ConfirmPasswordChanged -> {
                        this.confirmPassword = event.confirmPassword
                    }

                    is RegistrationEvent.PhoneNumberChanged -> {
                        this.phoneNumber = event.phoneNumber
                    }

                    is RegistrationEvent.LoadUser -> {
                       _uiState.value.build{
                           user = event.user
                       }
                    }

                    is RegistrationEvent.VerificationCodeChanged -> {
                        this.verificationCode = event.code
                    }

                    RegistrationEvent.ResendVerificationCode -> {
                        if(localUser != null) {
                            loading = true
                            resendCode()
                        }
                        else{
                                error = "No user found"

                        }
                    }
                }
            }
        }
        _uiState.value = uiState.value.build {
            this.isRegistrationContentValid =
                    this.fullname.trim().isNotEmpty() && this.email.trim().isNotEmpty() && Validation.isEmail(this.email)
                    && password.trim().isNotEmpty() && confirmPassword.trim().isNotEmpty()
                    && confirmPassword.trim() == password.trim()
            this.isVerifyCodeContentValid = this.verificationCode.trim().isNotEmpty() && verificationCode.length >= 4

        }

    }



    private fun submitVerificationCode(doneVerifying : () -> Unit) {
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {

                  val result =   authenticate.verifyUserCode(
                        localUser?.id ?: "",
                        _uiState.value.verificationCode
                    )

                if(result != null){
                    if(result.success){
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = null
                        }
                        sharedPrefs.setIsVerified(true)
                        navigationManager.commands = MutableStateFlow(HomeDirections.Default)
                        _uiState.value = uiState.value.build {
                            verificationDone = true
                        }
                        doneVerifying()


                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = result.message
                        }
                        //sharedPrefs.setAuthToken("")
                        //sharedPrefs.setRefreshToken("")
                    }
                }
                else{
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Failed to verify code"
                    }
                    //sharedPrefs.setAuthToken("")
                    //sharedPrefs.setRefreshToken("")
                    //userDao.deleteAll()
                }
            }
            catch (ex : Exception){
                Log.e("VerificationError", ex.message.toString())
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }

        }

    }
    private fun resendCode(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val res = authenticate.resendCode(localUser!!.id,true)
                if(res != null) {

                    if (res.success) {

                        _uiState.value = uiState.value.build {
                            loading = false
                            successMessage = "Code successfully resent"
                            error = null
                        }
                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = res.message
                        }

                    }
                }
                else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Error resending code. Try again"
                    }
                }
            }
            catch(ex: Exception){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Error resending code. Try again"
                }
            }
        }
    }

    private suspend fun processRegistrationResult(result : SignupResult?){
        if (result != null) {
            if(result.success) {
                //secret key


                var usr =  User(
                    name = result.user.name,
                    id = result.user.id,
                    username = result.user.email,
                    email = result.user.email
                )
                userDao.insertAll(
                    usr
                )
                _uiState.value.build {
                    this.user = usr
                }
                sharedPrefs.setAuthToken(result.token)
                sharedPrefs.setRefreshToken(result.refreshToken)
                sharedPrefs.setIsVerified(false)

                navigationManager.navigate(AuthenticationDirections.verifyAccount)
            }
            else{
                _uiState.value = uiState.value.build {
                    loading = false
                    error = "Failed to register"
                }
            }

        } else {
            _uiState.value = uiState.value.build {
                loading = false
                error = "Error registering. Try again"
            }
        }
    }
    private fun register() {
        _uiState.value = uiState.value.build {
            loading = true
            error = null
        }
        viewModelScope.launch {
            try {
                val result = authenticate.signUp(
                    userName = _uiState.value.email,//_uiState.value.username,
                    fullName = _uiState.value.fullname,
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    phoneNumber = _uiState.value.phoneNumber,
                )
                if (result != null) {
                    Log.e("registration", result.token)
                }
                processRegistrationResult(result)
                _uiState.value = uiState.value.build {
                    loading = false
                    error = null
                }
            }
            catch(ex : ServerException){
                ex.message?.let { Log.e("registration", it) }
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
            catch (ex : Exception){
                ex.message?.let { Log.e("registration2", it) }
                ex.printStackTrace()
                Log.e("registration2",ex.stackTraceToString())
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
        }
    }

    private fun socialLogin(){
        _uiState.value = uiState.value.build {
            loading = true
            error = null

        }
        viewModelScope.launch {
            Log.d("Login", "Within social login method")
            try {
                val loggedBefore = sharedPrefs.socialLogin.first();
                _uiState.value = uiState.value.build {
                    socialMediaFirstTime = loggedBefore
                }
                val res = authenticate.socialSignIn(
                    fullName = _uiState.value.fullname,
                    username = _uiState.value.username,
                    emailAddress = _uiState.value.email,
                    service = _uiState.value.socialMediaService,
                    token = _uiState.value.socialMediaToken,
                    profileImageUrl = _uiState.value.profileImageUrl,
                    isFirstTime = _uiState.value.socialMediaFirstTime
                )
                if(res != null){
                    Log.d("Login", "we got results")
                    if(res.success){
                        if(_uiState.value.socialMediaFirstTime){
                            userDao.insertAll(
                                User(
                                    name = res.user.name,
                                    id = res.user.id,
                                    username = res.user.email,
                                    email = res.user.email,
                                    )
                            )
                            //secret key


                        }
                        sharedPrefs.setSocialLogin(true)
                        sharedPrefs.setAuthToken(res.token)
                        sharedPrefs.setIsVerified(true)
                        navigationManager.commands = MutableStateFlow(HomeDirections.Default)
                        _uiState.value = uiState.value.build {
                            verificationDone = true
                        }
                        //navigationManager.navigate(HomeDirections.home)
                    }
                    else{
                        _uiState.value = uiState.value.build {
                            loading = false
                            error = "Wrong credentials. Please try again"
                        }
                        sharedPrefs.setAuthToken("")
                    }
                }
                else {
                    _uiState.value = uiState.value.build {
                        loading = false
                        error = "Error login. Try again"
                    }
                }
            }
            catch(ex : ServerException){
                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
            catch (ex : Exception){

                _uiState.value = uiState.value.build {
                    loading = false
                    error = ex.message
                }
            }
        }
    }
}