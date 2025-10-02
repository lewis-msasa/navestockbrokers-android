package com.nafepay.common_ui.composers.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.R

@Composable
fun SearchField(
    value : TextFieldValue,
    placeholder : String,
    onChange : (TextFieldValue) -> Unit,
    width : Dp,
    height : Dp,
    padding : Dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    onClose : () -> Unit = {}
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ){

        val textBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        TextField(
            value = value,
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.surface,
                textAlign = TextAlign.Justify),
            onValueChange = onChange,
            colors = TextFieldDefaults.colors(
                //textColor = MaterialTheme.colorScheme.surface,
                //backgroundColor = textBackgroundColor,

                cursorColor = MaterialTheme.colorScheme.primary,
                //focusedIndicatorColor = MaterialTheme.colorScheme.primary,
               // unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                //placeholderColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)

            ),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = {
                Icon(
                    painterResource(R.drawable.ic_search),
                    "",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        //.padding(top = 5.dp, bottom = 5.dp)
                        .height((height * 0.9f))

                )
            },

            trailingIcon = {
                if(!value.text.equals(""))
                    Icon(
                        painterResource(R.drawable.ic_x),
                        "",
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            //.padding(top = 5.dp, bottom = 5.dp)
                            .height((height * 0.9f))
                            .clickable {
                                onClose()
                            }

                    )
            },
            placeholder = { Text(placeholder,textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary) },
            modifier = Modifier
                .width(width)
                //.height(height)
                .padding(horizontal = padding)
                .clip(shape = RoundedCornerShape(50))
        )
    }
}