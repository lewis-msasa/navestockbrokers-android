package com.nafepay.common_ui.composers.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.utils.BottomSheetOption

@Composable
fun BottomSheetContent(
    bottomSheetOptions: List<BottomSheetOption>,
    header : @Composable ColumnScope.() -> Unit = {}
){
    Box(

    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.onSurface)
            //.height(200.dp)
        ) {
            Column(modifier =
            Modifier.padding(vertical = 20.dp),

                ) {
                header()
                bottomSheetOptions.forEach {

                    Row(
                        modifier = Modifier
                            .clickable {
                                it.action()
                            }
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(it.icon != null) {
                            Icon(
                                painter = painterResource(it.icon),
                                contentDescription = "",
                                modifier = Modifier.width(50.dp),
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                        Spacer(Modifier.padding(horizontal = 4.dp))
                        Text(text = it.text,
                            color = MaterialTheme.colorScheme.surface,
                        )
                    }
                }

            }
        }

    }
}