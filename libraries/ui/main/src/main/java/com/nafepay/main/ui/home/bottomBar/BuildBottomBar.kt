package com.nafepay.main.ui.home.bottomBar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.theme.DarkGrey
import com.nafepay.common_ui.theme.bottomTabHeight
import com.nafepay.navigation.BackPageData
import com.nafepay.navigation.NavigationCommand
import com.nafepay.navigation.Screen
import com.nafepay.navigation.tabItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildBottomBar(
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    selectedItemColor: Color = MaterialTheme.colorScheme.primary,
    unSelectedItemColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    commonState: CommonState,
    events: (CommonEvent) -> Unit,
    navigate: (route: NavigationCommand) -> Unit,
    currentTab: String,
): @Composable () -> Unit {
    val context = LocalContext.current
    return {
        if (commonState.hasBottomBar) {
            NavigationBar(
                modifier = Modifier
                    .height(bottomTabHeight)
                    .zIndex(1f),
                containerColor = backgroundColor,
                contentColor = unSelectedItemColor
            ) {
                tabItems.forEach { screen ->
                    val size = if (currentTab == screen.route.destination) 60.dp else 30.dp
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(screen.iconResourceId!!),
                                contentDescription = null,
                                modifier = Modifier.size(size)
                            )
                        },
                        label = {
                            Text(stringResource(screen.resourceId!!))
                        },
                        selected = currentTab == screen.route.destination,
                        onClick = {
                            events(CommonEvent.ChangeTab(screen))
                            events(CommonEvent.ChangeHasDeepScreen(false, ""))
                            events(CommonEvent.ChangeBackPageData(BackPageData()))
                            navigate(screen.route)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = selectedItemColor,
                            selectedTextColor = selectedItemColor,
                            unselectedIconColor = unSelectedItemColor,
                            unselectedTextColor = unSelectedItemColor,
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    }
}
