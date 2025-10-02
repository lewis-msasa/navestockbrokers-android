package com.nafepay.common_ui.composers.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.theme.commonPadding


@Composable
fun mainNewsBar(
    backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
    tintColor : Color =   MaterialTheme.colorScheme.surface,
    size : Dp = 60.dp,
    searchClicked : () -> Unit
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .background(backgroundColor)
    ){
        Text(
            "News",
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(horizontal = commonPadding),
            style = MaterialTheme.typography.titleSmall.copy(
                tintColor,
                fontWeight = FontWeight.Bold,
            ),
        )
        /*Icon(
            painterResource(R.drawable.ic_news),
            "",
            tint = tintColor,
            modifier = Modifier
                .padding(horizontal = commonPadding)
                .height(size * 0.3f)
                .width(size * 0.3f)
                .clickable {
                    searchClicked()
                }

        )*/
    }
}