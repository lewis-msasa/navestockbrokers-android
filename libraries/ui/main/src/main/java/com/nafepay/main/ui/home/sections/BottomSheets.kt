package com.nafepay.main.ui.home.sections

import android.content.Context
import android.util.Log
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.lifecycle.LifecycleOwner
import com.nafepay.authentication.events.UsersEvent
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.states.CommonState
import com.nafepay.common_ui.utils.BottomSheetOption
import com.nafepay.main.ui.general.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
fun userBottomSheet(
    events: (CommonEvent) -> Unit,
    userEvents: (UsersEvent) -> Unit,
    scope: CoroutineScope,
    sheetState: SheetState,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    userName: String = "",
) {
    bottomSheetAction(
        scope = scope,
        sheetState = sheetState,
        bottomOptions = listOf(
            BottomSheetOption(null, "Share Profile") {
                shareProfile(share = "", context)
                scope.launch { sheetState.hide() }
            },
            BottomSheetOption(null, "Block") {
                copyToClipboard("", context)
                scope.launch { sheetState.hide() }
            }
        ),
        events = events
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun bottomSheetAction(
    scope: CoroutineScope,
    sheetState: SheetState,
    bottomOptions: List<BottomSheetOption>,
    events: (event: CommonEvent) -> Unit
) {
    scope.launch {
        if (sheetState.currentValue == SheetValue.Hidden) {
            sheetState.show()
            events(CommonEvent.GetBottomSheetOptions(bottomOptions))
        } else {
            sheetState.hide()
        }
    }
}





