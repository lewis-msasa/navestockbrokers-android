package com.nafepay.common_ui.composers.sections

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PopupMenu(
    modifier: Modifier = Modifier,
    menuItems: List<String>,
    onClickCallbacks: List<() -> Unit>,
    showMenu: Boolean,
    onDismiss: () -> Unit,
) {
    DropdownMenu(
        modifier = modifier,
        expanded = showMenu,
        onDismissRequest = { onDismiss() },
    ) {
        menuItems.forEachIndexed{ index,item ->
            DropdownMenuItem(
                text = { Text(text = item) },
                onClick = {
                onDismiss()
                onClickCallbacks[index]()
            })
        }
    }
}