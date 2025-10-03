package com.nafepay.main.ui.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.utils.helpers.ShimmerAnimation
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.states.UsersState
import com.nafepay.authentication.viewModels.UsersViewModel
import com.nafepay.common_ui.composers.sections.SearchField
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.theme.bottomTabHeight
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.common_ui.viewModels.CommonViewModel
import com.nafepay.main.events.MainEvent
import com.nafepay.main.states.MainState
import com.nafepay.main.ui.home.HomeGeneralScreen
import com.nafepay.main.viewModels.MainViewModel


@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    commonViewModel: CommonViewModel,
    mainViewModel: MainViewModel,
    usersViewModel: UsersViewModel


) {
    val state by mainViewModel.uiState.collectAsState()
    val commonState by commonViewModel.uiState.collectAsState()
    val usersState by usersViewModel.uiState.collectAsState()
   Search(
       commonState = commonState,
       mainState = state,
       events = commonViewModel::handleCommonEvent,
       mainEvents = mainViewModel::handleMainEvent,
       usersState = usersState,
       usersEvents = usersViewModel::handleUsersEvent

   )
}

@ExperimentalComposeUiApi
@Composable
private fun Search(
    commonState: CommonState,
    mainState : MainState,
    events: (event: CommonEvent) -> Unit,
    mainEvents: (event: MainEvent) -> Unit,
    usersState: UsersState,
    usersEvents : (event : UsersEvent) -> Unit
) {
    HomeGeneralScreen(
        commonState = commonState,
        events = events,
        swipeToRefreshAction = {

        },
        backHandler = {
            events(CommonEvent.ChangeHasDeepScreen(false,""))
            events(CommonEvent.HasSearched(false))
            events(CommonEvent.NavigateUp)
        }
    ) {
        val scrollState = rememberScrollState(0)
        val keyboardController = LocalSoftwareKeyboardController.current
        BoxWithConstraints {
            val totalWidth = maxWidth
            Column(
                modifier = Modifier
                    .verticalScroll(
                        scrollState
                    )
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                var searchText by remember { mutableStateOf(TextFieldValue(usersState.searchUserText)) }
                SearchField(
                    value = searchText,
                    placeholder = "sermons",
                    onChange = {
                        usersEvents(UsersEvent.SearchUserTextChanged(it.text))
                        searchText = it
                    },
                    width = totalWidth,
                    height = 50.dp,
                    padding = totalWidth * 0.1f,
                    keyboardOptions = KeyboardOptions(

                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            //do search
                            if (searchText.text.isNotEmpty()) {
                                usersEvents(UsersEvent.SearchUser(searchText.text))
                                events(CommonEvent.HasSearched(true))
                            }
                        }
                    ),
                    onClose = {

                        mainEvents(MainEvent.SearchTextChanged(""))
                        searchText = TextFieldValue()
                        events(CommonEvent.HasSearched(false))

                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                if(commonState.hasSearchResult){
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = commonPadding)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Search Results for '${usersState.searchUserText}'",
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall.copy(
                                MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp

                            ),
                        )
                    }
                }
                else{
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = commonPadding)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Recent Searches",
                            modifier = Modifier.padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall.copy(
                                MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp

                            ),
                        )
                        Text(
                            "Clear",
                            modifier = Modifier.clickable {
                                usersEvents(UsersEvent.ClearRecentUserSearch)
                            },
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleSmall.copy(
                                Color.DarkGray,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp

                            ),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if(usersState.isLoading){
                    ShimmerAnimation(size = 100.dp, isCircle =false )
                }
                Column {

                    Spacer(modifier = Modifier.height(10.dp))
//                    MusicSection(
//                        title = "Sermons",
//                        hasSearchResult = commonState.hasSearchResult,
//                        musicState.recentSongSearch, musicEvents
//                    )
                }

                Spacer(modifier = Modifier.height(commonPadding))
                Spacer(modifier = Modifier.height(bottomTabHeight))

            }

        }
    }


}







