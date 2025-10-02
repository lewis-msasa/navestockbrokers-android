package com.nafepay.common_ui.composers.buttons

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.nafepay.common_ui.R
import com.nafepay.common_ui.theme.FBBlue
import com.nafepay.common_ui.theme.buttonHeight
import com.nafepay.common_ui.theme.commonPadding

@Composable
fun FacebookButton(width: Dp, action : () -> Unit) {

    Button(
        shape = RoundedCornerShape(40),
        modifier = Modifier
            .width(width)
            .height(buttonHeight)
            .padding(horizontal = commonPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = FBBlue,

            ),
        border = BorderStroke(
            color = Color.Gray,
            width = 1.dp
        ),
        onClick = {
            action()
            Log.d("FB clicked", "Clicked")
        }) {
        Row() {
            Image(
                painter = painterResource(R.drawable.ic_facebook_),
                "facebook",

                )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                "Login with Facebook",
                color = Color.White
            )
        }


    }
}