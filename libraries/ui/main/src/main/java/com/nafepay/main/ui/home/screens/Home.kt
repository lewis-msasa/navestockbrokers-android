package com.nafepay.main.ui.home.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.states.UsersState
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.theme.bottomTabHeight
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.ui.home.HomeGeneralScreen
import com.nafepay.navigation.BackPageData
import com.nafepay.navigation.Screen

@Composable
fun Home(
    commonViewModel: CommonViewModel,
    usersViewModel: UsersViewModel,
) {
    val state by commonViewModel.uiState.collectAsState()
    val usersState by usersViewModel.uiState.collectAsState()
    homeScreen(
        commonState = state,
        events = commonViewModel::handleCommonEvent,
        usersState = usersState,
        usersEvents = usersViewModel::handleUsersEvent
    )
}
@Composable
private fun homeScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    usersState: UsersState,
    usersEvents: (event: UsersEvent) -> Unit,
) {
    HomeGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {
            events(CommonEvent.OnRefresh)
            events(CommonEvent.OnEndRefresh)
        }
    ) {

        val scrollState = rememberScrollState(0)
        BoxWithConstraints() {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            Column(
                modifier = Modifier
                    //.statusBarsPadding()

                    .verticalScroll(
                        scrollState
                    )
            ) {


                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.3f)
                ) {



                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.surface.copy(0.5f),
                                        MaterialTheme.colorScheme.surface
                                    )
                                )
                            )
                            .align(Alignment.BottomCenter)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))
            }


        }
    }
}

