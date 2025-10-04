package com.nafepay.common_ui.composers.buttons

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.theme.buttonHeight
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.common_ui.R
import com.nafepay.common_ui.theme.zeroPadding


@Composable
fun GoogleButton(width: Dp, action : () -> Unit){
    Button(
        shape = RoundedCornerShape(2),
        modifier = Modifier
            .width(width)
            .background(MaterialTheme.colorScheme.surface)
            .height(buttonHeight)
            .padding(horizontal = commonPadding),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.surface,

            ),
        border = BorderStroke(
            color = Color.Gray,
            width = 0.dp
        ),
        onClick = {
            Log.d("Clicked","CLicked")
            action();
        }) {
        Row() {
            Image(
                painter = painterResource(R.drawable.ic_google),
                "google",

                )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                "Login with Google",
                color = MaterialTheme.colorScheme.onSurface
            )
        }


    }
}