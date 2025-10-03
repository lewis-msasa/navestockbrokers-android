package com.navepay

import android.app.AlertDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.nafepay.authentication.viewModels.RegistrationViewModel
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.composers.general.LoadingBox
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.theme.NafeTheme
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.core.di.Preferences
import com.nafepay.main.ui.home.MainTabUI
import com.nafepay.main.utils.helpers.LocalSysUiController
import com.nafepay.main.utils.helpers.SystemUiController
import com.nafepay.main.viewModels.MainViewModel
import com.nafepay.navigation.GeneralDirections
import com.nafepay.navigation.NavigationManager
import com.navepay.ui.theme.StockbrokerTheme
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private var account: GoogleSignInAccount? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val mainViewModel: MainViewModel by viewModels()
    private val commonViewModel: CommonViewModel by viewModels()

    private val registrationViewModel: RegistrationViewModel by viewModels()
    private val usersViewModel : UsersViewModel by viewModels()

    val navigationManager : NavigationManager by inject()

    val sharedPrefs : Preferences by inject()

    //Network

    val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            showToast("Online")

        }
        override fun onLost(network: Network) {
            showToast("Connection Lost")
        }
    }


    private fun listenForInternetConnectivity() {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun unregisterListenerForInternetConnectivity() {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }


    //end of network


    @OptIn(ExperimentalFoundationApi::class, InternalCoroutinesApi::class,
        ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //subscribeToFcmTopic("sermons")
//        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
//            if (!TextUtils.isEmpty(token)) {
//                Log.d("token", "retrieve token successful : $token")
//            } else {
//                Log.w("token", "token should not be null...")
//            }
//        }.addOnFailureListener { e -> }
//            .addOnCanceledListener {}.addOnCompleteListener { task ->
//                if(task.isSuccessful) {
//                    Log.v(
//                        "token",
//                        "This is the token : " + task.result
//                    )
//                }
//            }
        listenForInternetConnectivity()


        setContent {
            val systemUiController = remember { SystemUiController(window) }
            val navController = rememberNavController()
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {

                NafeTheme {


                    window.statusBarColor = MaterialTheme.colorScheme.onSurface.toArgb()


                    navigationManager.commands.collectAsState().value.also{ command ->
                        if(command.destination.isNotEmpty()){
                            if(command.destination == GeneralDirections.back.destination) {

                                navController.navigateUp()
                                navigationManager.commands.value = GeneralDirections.Default

                            }
                            else{
                                LoadingBox()
                                var destination = command.destination


                                navController.navigate(destination)

                            }

                        }

                    }
                    //var dir =  com.fov.common_ui.utils.helpers.Utilities.getDataDirectory(applicationContext).absolutePath
                    commonViewModel.users.observe(this) { users ->
                        users?.let {
                            if (users.count() > 0) {
                                val user = users.first()
                                val name = user.name

                                commonViewModel.handleCommonEvent(CommonEvent.LoadUser(user))
                                commonViewModel.handleCommonEvent(CommonEvent.ChangeUserId(user.id))
                                (application as NaveStockApp).user = user

                            }
                        }
                    }
                    val context = LocalContext.current

                    Box(
                        modifier = Modifier.systemBarsPadding()
                    ){
                        MainTabUI(
                            navController,
                            mainViewModel,
                            usersViewModel,
                            commonViewModel,
                        ){
                            navigationManager.navigate(it)
                        }
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterListenerForInternetConnectivity()
    }

//    private fun subscribeToFcmTopic(topic:String){
//        Firebase.messaging.subscribeToTopic(topic)
//            .addOnCompleteListener { task ->
//                var msg = "Subscribed"
//                if (!task.isSuccessful) {
//                    msg = "Subscribe failed"
//                }
//                Log.d("", msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            }
//    }

    private fun showAlert(title: String, message: String? = null) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this,  message, Toast.LENGTH_LONG).show()
        }
    }
}