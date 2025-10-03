package com.nafepay.common_ui.composers.headers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.theme.NafeTheme
import com.nafepay.navigation.BackPageData
import com.nafepay.common_ui.R

@Preview(showBackground = true)
@Composable
fun prevBackTopBar(){
    NafeTheme {
        backTopBar(title = "@Enyo", backAction = {isDeep, title ->

        }, BackPageData()){
            Image(
                painterResource(R.drawable.ic_more_vertical),
                "",
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                    ),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}


@Composable
fun backTopBar(title : String,
               backAction : (isDeep: Boolean, title : String) -> Unit,
               backPageData : BackPageData,
               size : Dp = 60.dp,
               tintColor : Color =   MaterialTheme.colorScheme.surface,
               backgroundColor: Color = MaterialTheme.colorScheme.onSurface,
               extraComposable :  @Composable() () -> Unit = {
                   Spacer(modifier = Modifier.width(10.dp))
               }
){


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .background(backgroundColor)
    ) {


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 6.dp),
        ){
            Icon(painter = painterResource(R.drawable.ic_back_arrow),
                modifier = Modifier
                    .clickable {
                        backAction(
                            backPageData.IsDeep,
                            backPageData.Title
                        )
                    }
                    .height((size / 2))
                    .padding(horizontal = 6.dp),
                tint = tintColor,
                contentDescription = "")
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall.copy(
                    tintColor,
                    fontWeight = FontWeight.Bold,
                ),
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        extraComposable()


    }
}