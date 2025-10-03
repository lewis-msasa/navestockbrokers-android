package com.nafepay.main.ui.home.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.models.Notification
import com.nafepay.authentication.states.UsersState
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.extensions.itemsCustomized
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.theme.padding10
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.ui.home.HomeGeneralScreen
import kotlinx.coroutines.flow.Flow
import androidx.compose.foundation.lazy.LazyListScope
import com.nafepay.common_ui.composers.sections.notificationsSingleRow
import com.nafepay.common_ui.theme.bottomTabHeight
import com.nafepay.common_ui.theme.commonPadding


@Composable
fun NotificationScreen(
    commonViewModel: CommonViewModel,
    userViewModel: UsersViewModel
) {
//        val state by commonViewModel.uiState.collectAsState()
//        val usersState by userViewModel.uiState.collectAsState()
//        val notificationsPaging = userViewModel.notificationsPaging
//        NotificationsScreen(
//            commonState = state,
//            events = commonViewModel::handleCommonEvent,
//            usersState = usersState,
//            usersEvents = userViewModel::handleUsersEvent,
//            notificationsPaging = notificationsPaging
//        )
}


@Composable
private fun NotificationsScreen(
    commonState: CommonState,
    events : (event: CommonEvent) -> Unit,
    usersState:UsersState,
    usersEvents : (event:UsersEvent) -> Unit,
    notificationsPaging : Flow<PagingData<Notification>>
) {
    val notifications = notificationsPaging.collectAsLazyPagingItems()
    HomeGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {
            usersEvents(UsersEvent.LoadNotifications)
        },
        backHandler = {
            events(CommonEvent.ChangeHasDeepScreen(false, ""))
            events(CommonEvent.NavigateUp)
        }
    ) {
        Box() {

            val scrollState = rememberScrollState(0)
            val expanded = remember { mutableStateOf(false) }
            if (notifications != null) {
                LazyColumn(

                ) {

                    itemsCustomized(notifications) { notification, _ ->
                        notificationsSingleRow(
                            100.dp,
                            notification?.notifier ?: "",
                            notification?.notifierImgUrl ?: "",
                            notification?.notification ?: "",
                            notification?.friendlyTime ?: ""
                        ) {

                                    Button(
                                        onClick = {

                                        },
                                        shape = RoundedCornerShape(10),
                                    ) {


                                        Text("Follow", color = Color.White)
                                    }

                            }
                        Spacer(modifier = Modifier.padding(vertical = padding10))
                        }



                    }
                    notifications.apply {

                        when {
                            loadState.refresh is LoadState.Loading -> {

                                    Box(
                                        modifier = Modifier
                                            .background(color = MaterialTheme.colorScheme.background),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.testTag("TAG_PROGRESS"),
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }

                            }
                            loadState.append is LoadState.Loading -> {
                                ShimmerAnimation(size = 60.dp, isCircle = true)
                            }
                            loadState.refresh is LoadState.Error -> {
                                val e = notifications.loadState.refresh as LoadState.Error

                            }
                            loadState.append is LoadState.Error -> {
                                val e = notifications.loadState.append as LoadState.Error

                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))
            }




        }
    }


