package com.nafepay.main.ui.home.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.viewModels.MainViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import com.nafepay.main.ui.home.screens.Home
import com.nafepay.main.ui.home.screens.NotificationScreen
import com.nafepay.main.ui.home.screens.SearchScreen
import com.nafepay.main.ui.profile.screens.UserProfileScreen
import com.nafepay.navigation.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel
) {

    NavHost(
        navController,
        modifier = Modifier.zIndex(-1f),
        startDestination = AuthenticationDirections.mainTab.destination,

        ) {

        navigation(
            startDestination =  HomeDirections.home.destination,
            route = AuthenticationDirections.mainTab.destination
        ) {

            composable(
                HomeDirections.home.destination,
                arguments = HomeDirections.home.arguments
            ) {

                Home(
                    commonViewModel,
                    usersViewModel
                )
                /*PaymentOptionsScreen(
                    commonViewModel = commonViewModel,
                    paymentViewModel = paymentViewModel )*/

            }


            composable(
                HomeDirections.profile.destination,
                arguments = HomeDirections.profile.arguments
            ) {

                UserProfileScreen(
                    usersViewModel,
                    commonViewModel
                )
            }




            composable(
                HomeDirections.notifications.destination,
                arguments = HomeDirections.notifications.arguments
            ) {

                NotificationScreen(
                    commonViewModel,
                    usersViewModel
                )
            }
            composable(
                HomeDirections.search.destination,
                arguments = HomeDirections.search.arguments

            ) {

                SearchScreen(
                    commonViewModel = commonViewModel,
                    mainViewModel = mainViewModel,
                    usersViewModel = usersViewModel,
                )
            }



        }
    }
}