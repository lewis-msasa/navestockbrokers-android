package com.nafepay.main.ui.profile.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.authentication.states.UsersState
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState

@Composable
fun profileTabs(
    commonState: CommonState,
    commonEvents : (CommonEvent) -> Unit
){

    var selectedTabState by remember { mutableStateOf(Tabs.Music) }
    var backgroundColor = MaterialTheme.colorScheme.surface
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        SecondaryTabRow(
            selectedTabIndex = selectedTabState.ordinal,
            containerColor = backgroundColor,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(selectedTabIndex),
                    height = 4.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(40.dp)
        ) {
            // First Tab
            Tab(
                selected = Tabs.Music == selectedTabState,
                onClick =  { selectedTabState = Tabs.Music },
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "Saved Sermons",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                )
            }

            // Optional second tab
            /*
            Tab(
                selected = Tabs.Activity == selectedTabState,
                onClick = { onTabSelected(Tabs.Activity) },
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "Activity",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            */
        }
//        when (selectedTabState) {
//
//            /*Tabs.Activity ->{
//                if(usersState.userModel!!.isSubscribed)
//                    ActivityTab(
//                        playlistState.userPlaylists,
//                        events
//                    )
//
//            }*/
//
//        }
    }

}

enum class Tabs {
    Music,
    //Activity,

}
