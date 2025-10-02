package com.nafepay.common_ui.composers.general

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.core.net.toUri




@Composable
fun ClickableTextLink(annotatedString : AnnotatedString,
                      urlTags : List<String>,
                      modifier : Modifier

){
    val context = LocalContext.current
    //val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        //textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall.copy(
            MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            urlTags.forEach{urlTag ->
                annotatedString
                    .getStringAnnotations(urlTag, it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        // uriHandler.openUri(stringAnnotation.item)
                        val intent = Intent(Intent.ACTION_VIEW, stringAnnotation.item.toUri())
                        context.startActivity(intent)
                    }
            }

        }
    )

}

@Composable
fun ClickableTextAction(
    annotatedString : AnnotatedString,
    urlTags : List<String>,
    actions:  List<() -> Unit>,
    modifier : Modifier

){


    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = MaterialTheme.typography.titleSmall.copy(
            MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            urlTags.forEachIndexed{ index, _ ->
                actions[index]()
            }

        }
    )

}