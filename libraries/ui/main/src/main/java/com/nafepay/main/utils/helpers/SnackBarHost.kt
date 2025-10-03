package com.nafepay.main.utils.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.nafepay.common_ui.composers.sections.DarkOverlay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun setUpSnackBar(message : String?, state : SnackbarHostState, scope : CoroutineScope){
    if (!message.isNullOrEmpty()) {
        Log.d("SNACKBAR", message)
        scope.launch {
            var result = state.showSnackbar(
                message = message
            )

        }
    }
}
fun setUpToast(message:String?,context : Context){
    if(!message.isNullOrEmpty()){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setUpBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope
){
    if (sheetState.currentValue != SheetValue.Hidden)
        DarkOverlay {
            scope.launch {

                if (sheetState.currentValue == SheetValue.Hidden) {
                    sheetState.show()

                } else {
                    sheetState.hide()
                }
            }
        }
}