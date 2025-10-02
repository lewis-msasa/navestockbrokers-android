package com.nafepay.common_ui.composers.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.R

@Composable
fun Section(title : String,
            action : () -> Unit,
            showSeeAll : Boolean = false,
            seeAllAction : () -> Unit = {},
            content: @Composable ColumnScope.() -> Unit) {
    Column(
        //verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().clickable {
            action
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(vertical = 10.dp)
        ) {
            Text(
                title,
                textAlign = TextAlign.Start,
                modifier = Modifier.alignByBaseline(),
                style = MaterialTheme.typography.titleSmall.copy(
                    MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                ),
            )
            if (showSeeAll) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        "See All",
                        modifier = Modifier.alignByBaseline(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodySmall.copy(
                            MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_forward),
                        modifier = Modifier.clickable {
                            seeAllAction()
                        }.height(20.dp)
                            .padding(horizontal = 6.dp),
                        contentDescription = ""
                    )
                }
            }

        }
        content()
    }
}

