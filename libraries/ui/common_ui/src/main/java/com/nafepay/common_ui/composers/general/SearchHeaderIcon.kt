package com.nafepay.common_ui.composers.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.R
@Composable
fun searchHeaderIcon(
    tintColor : Color =   MaterialTheme.colorScheme.surface,
    onAction :  ()  -> Unit){
    Image(
        painterResource(R.drawable.ic_search),
        "",
        modifier = Modifier
            .padding(
                horizontal = 10.dp,
            ).clickable{
                onAction()
            },
        colorFilter = ColorFilter.tint(tintColor)
    )

}