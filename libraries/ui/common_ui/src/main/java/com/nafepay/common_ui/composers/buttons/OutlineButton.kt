package com.nafepay.common_ui.composers.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun customOutlinedButton(text : String,
                         width: Dp? = null,
                         cornerShape : Int = 10,
                         imageContent: @Composable RowScope.() -> Unit = {},
                         action : () -> Unit){
    OutlinedButton(
        onClick = action,
        modifier = if(width != null) Modifier.width(width) else Modifier,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(cornerShape),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            imageContent()
            Spacer(modifier = Modifier.width(6.dp))
            Text( text = text )
        }
    }
}