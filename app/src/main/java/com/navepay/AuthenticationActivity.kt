package com.navepay

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nafepay.authentication.events.RegistrationEvent
import com.nafepay.authentication.ui.login.Login
import com.nafepay.authentication.ui.registration.Onboarding
import com.nafepay.authentication.ui.registration.Registration
import com.nafepay.authentication.ui.registration.VerifyAccount
import com.nafepay.authentication.viewModels.LoginViewModel
import com.nafepay.authentication.viewModels.RegistrationViewModel
import com.nafepay.common_ui.composers.general.EnterAnimation
import com.nafepay.common_ui.composers.general.LoadingBox
import com.nafepay.common_ui.theme.NafeTheme
import com.nafepay.common_ui.theme.ThemeHelper
import com.nafepay.common_ui.utils.helpers.NotificationUtils
import com.nafepay.core.di.Preferences
import com.nafepay.main.utils.helpers.LocalSysUiController
import com.nafepay.main.utils.helpers.SystemUiController
import com.nafepay.navigation.AuthenticationDirections
import com.nafepay.navigation.GeneralDirections
import com.nafepay.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AuthenticationActivity : AppCompatActivity() {

    private val registrationViewModel: RegistrationViewModel by viewModel()
    private val loginViewModel : LoginViewModel by viewModel()
    private var account: GoogleSignInAccount? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null


    val navigationManager : NavigationManager by inject()

    val sharedPrefs : Preferences by inject()


    private val RC_SIGN_IN = 1

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class,
        androidx.compose.material3.ExperimentalMaterial3Api::class,
        ExperimentalFoundationApi::class,
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        //notification channel
        NotificationUtils.createNotificationChannel(this)


        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {

                    NafeTheme  {
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        if (ThemeHelper.isDarkTheme())
                            window.statusBarColor = MaterialTheme.colorScheme.onPrimary.toArgb()
                        else
                            window.statusBarColor = MaterialTheme.colorScheme.primary.toArgb()

                        val token by sharedPrefs.accessToken.collectAsState(initial = null)
                        val isVerified by sharedPrefs.isVerified.collectAsState(initial = false)
                        val onboardingViewed by sharedPrefs.onboardingViewed.collectAsState(initial = false)

                        val context = LocalContext.current

                        val navController = rememberNavController()

                        val startDestination = AuthenticationDirections.onboarding.destination


                        navigationManager.commands.collectAsState().value.also{ command ->
                            if(command.destination.isNotEmpty()){
                                if(command.destination == GeneralDirections.back.destination) {

                                    navController.navigateUp()

                                }
                                else{
                                    LoadingBox()
                                    var destination = command.destination
                                    val dest = intent.getStringExtra("destination")
                                    if(dest != null){
                                        destination = dest
                                    }
                                    Log.d("NAVTO", destination)
                                    navController.navigate(destination)

                                }

                            }

                        }

                        EnterAnimation {

                            LoadingBox()
                        }






                        NavHost(navController,
                            startDestination = "START") {
                            Log.d("NAVIGATION", startDestination)
                            composable(
                                "START"
                            ) {
                                EnterAnimation {

                                    LoadingBox()
                                }


                            }
                            composable(
                                AuthenticationDirections.onboarding.destination
                            ) {
                                EnterAnimation {
                                    Onboarding(registrationViewModel)
                                }


                            }

                            composable(AuthenticationDirections.verifyAccount.destination) {
                                EnterAnimation {
                                    VerifyAccount(
                                        viewModel = registrationViewModel
                                    )
                                }
                            }
                            composable(AuthenticationDirections.login.destination) {
                                EnterAnimation {
                                    Login(
                                        loginViewModel = loginViewModel,
                                        OnGoogleSignIn = {
                                            onGoogleSignIn()
                                        },
                                    )
                                }
                            }
                            composable(AuthenticationDirections.registration.destination) {
                                EnterAnimation{

                                    Registration(
                                        viewModel = registrationViewModel,
                                        OnGoogleSignIn = {
                                            onGoogleSignIn()
                                        }
                                    )
                                }
                            }
                        }

                    }

            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            val context = applicationContext
            registrationViewModel.uiState.collect{ state ->
               if(state.verificationDone){
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    context.startActivity(intent)
                    finish()
                    this.cancel()
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            val context = applicationContext
            loginViewModel.uiState.collect{ state ->
                if(state.loginDone){
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    context.startActivity(intent)
                    finish()
                    this.cancel()
                }
            }

        }

        CoroutineScope(Dispatchers.Main).launch {
            sharedPrefs.onboardingViewed.collectLatest { onBoardingViewed ->
                sharedPrefs.accessToken.collectLatest { token ->
                    sharedPrefs.isVerified.collectLatest { verified ->
                        if ((verified == false || verified == null) && token != null) {
                            navigationManager.navigate(AuthenticationDirections.verifyAccount)
                        } else {
                             if(onBoardingViewed == true)
                                 navigationManager.navigate(AuthenticationDirections.registration)
                            else
                                navigationManager.navigate(AuthenticationDirections.onboarding)
                        }

                    }
                }
            }
            this.cancel()
        }
    }

    override fun onStart() {

        account = GoogleSignIn.getLastSignedInAccount(this)
        super.onStart()
        if(account != null) {
            handleGoogleLogin(account!!)
        }
    }

    private fun handleGoogleLogin( account: GoogleSignInAccount){

            var  events = registrationViewModel::handleRegistrationEvent
            account!!.email?.let {
                events(RegistrationEvent.EmailChanged(it))
            }
            account!!.displayName?.let {
                events(RegistrationEvent.FullNameChanged("${it}"))
            }
            events(RegistrationEvent.UsernameChanged(""))
            events(RegistrationEvent.SocialMediaServiceChanged("GOOGLE"))
            account!!.idToken?.let {
                events(RegistrationEvent.SocialMediaTokenChanged(it))
            }
            Log.d("Login", "we are in handle Login")
            events(RegistrationEvent.SocialMediaIsFirstTimeChanged(true))
            events(RegistrationEvent.SocialRegisterClicked)

    }

    private val getGoogleLoginResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            Log.d("Login", "within register for results ${it.data?.data} ${it.resultCode}")
            if(it.resultCode != RESULT_CANCELED){
                 val data = it.data
                Log.d("Login", "data ${it.data.toString()}")
                 val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                 account = task.getResult(ApiException::class.java)
                if(account != null) {
                    Log.d("Login", "Account ${account!!.email}")
                    handleGoogleLogin(account!!)
                }

            }
        }

    fun onGoogleSignIn() {
        mGoogleSignInClient?.signInIntent?.let {
            val signInIntent: Intent = it
            Log.d("Login","${it.data}")
             getGoogleLoginResult.launch(signInIntent)

        };

    }

}