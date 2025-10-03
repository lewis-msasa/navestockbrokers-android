package com.nafepay.main.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nafepay.common_ui.composers.general.LoadingBox
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import kotlinx.coroutines.launch

@Composable
fun HomeGeneralScreen(
    commonState : CommonState,
    events: (event: CommonEvent) -> Unit,
    swipeToRefreshAction : () -> Unit = {},
    backHandler : () -> Unit = {},
    pageContent : @Composable BoxScope.() -> Unit,
){

    BackHandler(onBack = {
        backHandler()
    })
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()

    ) {


        val context = LocalContext.current
        val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
        LaunchedEffect(commonState.toast) {
            lifecycleOwner.lifecycleScope.launch {

                if (commonState.toast != null) {
                    Toast.makeText(context, commonState.toast, Toast.LENGTH_SHORT).show()
                }


            }
        }
        // Manage the refreshing state
        var isRefreshing by remember { mutableStateOf(false) }

        // Define the onRefresh callback
        val onRefresh: () -> Unit = {
            isRefreshing = true
            swipeToRefreshAction()
        }

        // Create the PullToRefreshBox
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Display loading indicator or content
                if (commonState.loading) {
                    LoadingBox()
                } else {
                    pageContent()
                }

                // Optional bottom content
                Box(
                    modifier = Modifier
                        .height(280.dp)
                        .align(Alignment.BottomEnd)
                ) {
                    // Optional content here
                }
            }
        }

        // Handle the end of the refresh
        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                // Simulate async completion OR call after your refresh finishes
                // IMPORTANT: you must end the refresh, it's not automatic
                isRefreshing = false
            }
        }


    }

}