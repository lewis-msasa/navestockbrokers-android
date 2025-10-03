package com.nafepay.main.ui.profile.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.states.UsersState
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.ui.home.HomeGeneralScreen
import com.nafepay.navigation.BackPageData
import kotlinx.coroutines.launch
import com.nafepay.common_ui.R
import com.nafepay.common_ui.composers.general.LoadingBox
import com.nafepay.common_ui.theme.buttonHeight
import com.nafepay.main.ui.profile.sections.profileTabs
import com.nafepay.navigation.Screen

@Composable
fun UserProfileScreen(
    usersViewModel: UsersViewModel,
    commonViewModel: CommonViewModel
){
    val userState  by usersViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()

    ProfileScreen(
        userState,
        usersViewModel::handleUsersEvent,
        commonState,
        commonViewModel::handleCommonEvent
    )

}

@Composable
private fun ProfileScreen(
    userState: UsersState,
    userEvents: (UsersEvent) -> Unit,
    commonState: CommonState,
    commonEvents: (CommonEvent) -> Unit
)
{

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    HomeGeneralScreen(
        commonState = commonState,
        events = commonEvents,
        swipeToRefreshAction = {

            userEvents(UsersEvent.UserSelected(userState.userProfileId))
        },
        backHandler = {
            commonEvents(CommonEvent.ChangeHasDeepScreen(false,""))
            commonEvents(CommonEvent.NavigateUp)
        }
    ) {
        LaunchedEffect(commonState.hasDeepScreen) {

            commonEvents(CommonEvent.ChangeBackPageData(BackPageData()))
            if( userState.userModel != null) {
                //commonEvents(CommonEvent.ChangeHasDeepScreen(true, userState.userModel!!.name))
            }
            userEvents(UsersEvent.UserSelected(userState.userProfileId))


        }
        val scrollState = rememberScrollState(0)
        if(userState.isLoading){
            LoadingBox()
        }
        if( userState.userModel != null) {
            val user = userState.currentUserModel!!
            BoxWithConstraints {
                val screenWidth = maxWidth
                Column(
                    modifier = Modifier

                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(commonPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            userState.userModel!!.name,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleSmall.copy(
                                MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Text(
                            userState.userModel!!.email,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodySmall.copy(
                                MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Spacer(modifier = Modifier.height(commonPadding))

                        Button(
                            shape = MaterialTheme.shapes.medium,
                            enabled = true,
                            modifier = Modifier
                                .width(screenWidth * 0.6f)
                                .height(buttonHeight),

                            onClick = {
                               commonEvents(CommonEvent.ChangeBackPageData(BackPageData(true,userState.userModel!!.name)))
                               commonEvents(CommonEvent.ChangeHasDeepScreen(true, "Payment Options"))


                            }) {

                            Text(
                                "Subscribe",
                                color = MaterialTheme.colorScheme.surface
                            )


                        }


                    }
                    profileTabs(
                        commonState,
                        commonEvents
                    )


                }
            }
        }
    }


}