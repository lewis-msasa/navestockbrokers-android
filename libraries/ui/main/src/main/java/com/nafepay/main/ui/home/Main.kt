package com.nafepay.main.ui.home


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nafepay.main.utils.helpers.setUpBottomSheet
import com.nafepay.main.utils.helpers.setUpSnackBar
import com.nafepay.main.utils.helpers.setUpToast
import com.nafepay.main.ui.home.topBar.BuildTopBar
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.composers.general.BottomSheetContent
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.utils.constants.Constants
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.ui.home.bottomBar.BuildBottomBar
import com.nafepay.main.ui.home.navigation.NavigationHost
import com.nafepay.main.viewModels.MainViewModel
import com.nafepay.navigation.NavigationCommand
import com.nafepay.navigation.Screen
import kotlinx.coroutines.InternalCoroutinesApi
import com.nafepay.common_ui.composers.sections.DarkOverlay

@ExperimentalFoundationApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api

@Composable
fun MainTabUI(
    navController : NavHostController,
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    navigate : (route : NavigationCommand) -> Unit
){
    //val state by mainViewModel.uiState.collectAsState()
    val userState by usersViewModel.uiState.collectAsState()
    val state by commonViewModel.uiState.collectAsState()
    MainHome(
        navController,
        mainViewModel,
        usersViewModel,
        commonViewModel,
        navigate
    )
}
@ExperimentalFoundationApi
@InternalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun MainHome(
    navController : NavHostController,
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel,
    navigate : (route : NavigationCommand) -> Unit
){

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()
    val userState by usersViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    val events = commonViewModel::handleCommonEvent
    val userEvents = usersViewModel::handleUsersEvent


    val backgroundColor = MaterialTheme.colorScheme.onSurface
    val tintColor = MaterialTheme.colorScheme.surface



    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.arguments?.getString(Constants.MAINTAB)
        ?: Screen.Home.route.destination


        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(
                initialValue = SheetValue.Hidden
            ),
            snackbarHostState = snackbarHostState
        )

         //val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

         BottomSheetScaffold(
            sheetContent = {

                  BottomSheetContent(
                      bottomSheetOptions = commonState.bottomSheetOptions,
                      header = commonState.bottomSheetHeader
                  )

            },
            sheetContainerColor = Color.Transparent,
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
            sheetShape = RectangleShape
        ) {

          Box() {

           LaunchedEffect(commonState.currentTab){
               events(CommonEvent.ChangeTopBarColor(backgroundColor))
               events(CommonEvent.ChangeTopBarTintColor(tintColor))
           }
            Scaffold(

                snackbarHost = { SnackbarHost(snackbarHostState) },

                topBar = BuildTopBar(
                    backgroundColor = backgroundColor,
                    tintColor = tintColor,
                    scope = scope,
                    context = context,
                    sheetState = bottomSheetScaffoldState.bottomSheetState,
                    lifecycleOwner = lifecycleOwner,
                    commonState = commonState,
                    events = events,
                    userState = userState,
                    userEvents = userEvents,

                ),
                bottomBar = BuildBottomBar(
                    backgroundColor = backgroundColor,
                    commonState = commonState,
                    events = events,
                    navigate = navigate,
                    currentTab = currentTab
                )

            ) {
                 val bp = it.calculateBottomPadding()

                 NavigationHost(
                     navController,
                     mainViewModel,
                     usersViewModel,
                     commonViewModel,
                 )
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                )


            }



              setUpToast(commonState.toast,context)
              setUpSnackBar(commonState.errorMessage,snackBarHostState,scope)


              setUpBottomSheet(sheetState = bottomSheetScaffoldState.bottomSheetState, scope = scope)




              if(commonState.isShowingStory) {


                      Box{
                          DarkOverlay {
                              events(CommonEvent.ToggleShowStory(false))
                          }


                      }




              }
        }
      }
}










