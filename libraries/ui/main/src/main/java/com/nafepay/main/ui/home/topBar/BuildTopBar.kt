package com.nafepay.main.ui.home.topBar

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LifecycleOwner
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.states.UsersState
import com.nafepay.common_ui.composers.general.searchHeaderIcon
import com.nafepay.common_ui.composers.headers.backTopBar
import com.nafepay.common_ui.composers.headers.mainTopBar
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.main.ui.general.moreHeaderIcon
import com.nafepay.main.ui.home.sections.userBottomSheet
import com.nafepay.navigation.BackPageData
import com.nafepay.navigation.Screen
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildTopBar(
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    tintColor : Color =   MaterialTheme.colorScheme.surface,
    scope : CoroutineScope,
    context : Context,
    sheetState: SheetState,
    lifecycleOwner: LifecycleOwner,
    commonState: CommonState,
    events : (CommonEvent) -> Unit,
    userState: UsersState,
    userEvents : (UsersEvent) -> Unit,


    ) : @Composable () -> Unit {



    return {
        if (commonState.hasTopBar) {
            if (commonState.hasDeepScreen) {


                backTopBar(
                    title = commonState.screenTitle,
                    backgroundColor = commonState.topBarColor,
                    tintColor = commonState.topBarTintColor,
                    backAction = { isDeep, title ->
                        events(CommonEvent.ChangeHasDeepScreen(isDeep, title))
                        events(CommonEvent.ChangeShowMoreOptions(false))
                        events(CommonEvent.ChangeTopBarColor(backgroundColor))
                        events(CommonEvent.ChangeTopBarTintColor(tintColor))
                        events(CommonEvent.ChangeShowSearchOption(false))
                        events(CommonEvent.NavigateUp)
                    }, backPageData = commonState.backPageData
                ) {
                    if (commonState.showMoreOptions) {
                        var bottomSheetShowMoreContent: () -> Unit = {}
                        if (commonState.currentTab == Screen.Home) {
                            if (commonState.user?.id == userState.userModel?.id) {
                                bottomSheetShowMoreContent = {
//                                    ownerUserBottomSheet(
//                                        events = events,
//                                        userEvents = userEvents,
//                                        scope = scope,
//                                        bottomSheetScaffoldState = bottomSheetScaffoldState,
//                                        context = context,
//                                        lifecycleOwner = lifecycleOwner,
//                                        "@${commonState.user?.name}"
//
//                                    )
                                }
                            } else {
                                bottomSheetShowMoreContent = {
                                    userBottomSheet(
                                        events = events,
                                        userEvents = userEvents,
                                        scope = scope,
                                        sheetState = sheetState,
                                        context = context,
                                        lifecycleOwner = lifecycleOwner,
                                        userState.currentUserModel!!.name
                                    )
                                }

                            }
                        }

                        events(
                            CommonEvent.ChangeBottomSheetAction(
                                bottomSheetShowMoreContent
                            )
                        )
                        moreHeaderIcon(
                            tintColor = commonState.topBarTintColor,
                            onAction = bottomSheetShowMoreContent
                        )


                    } else if (commonState.showSearchOption) {
                        searchHeaderIcon(
                            tintColor = commonState.topBarTintColor
                        ) {
                            events(CommonEvent.ChangeShowSearchBar(true))
                            events(CommonEvent.ChangeHasTopBar(false))
                        }
                    }
                }
            } else {
                when (commonState.currentTab) {
                    Screen.Home -> {
                        mainTopBar(
                            backgroundColor = backgroundColor,
                            tintColor = tintColor,
                            numNotifications = userState.numNotifications,
                            notificationClicked = {
                                events(CommonEvent.ChangeHasDeepScreen(true, "Notifications"))
                                events(CommonEvent.ChangeBackPageData(BackPageData()))
                                events(CommonEvent.NotificationsViewed)
                                userEvents(UsersEvent.LoadNotifications)
                                events(CommonEvent.NavigateToNotifications)
                                //navController.navigate(Screen.Notifications.route.destination)
                            },
                            profileClicked = {
                                events(
                                    CommonEvent.ChangeHasDeepScreen(
                                        true,
                                        "${commonState.user?.name}"
                                    )

                                )
                                events(CommonEvent.ChangeBackPageData(BackPageData()))
                                events(CommonEvent.ChangeTab(Screen.Home))
                                userEvents(UsersEvent.GoToProfile)
                                userEvents(UsersEvent.UserSelected(commonState.user?.id ?: ""))
                            },
                            searchClicked = {

                                events(CommonEvent.ChangeHasDeepScreen(true, "Search"))

                                events(CommonEvent.HasSearched(false))
                                events(CommonEvent.NavigateToSearch)

                            }
                        )
                    }



                    else -> {

                    }
                }
            }
        }
    }

}



