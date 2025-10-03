package com.nafepay.main.ui.general

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nafepay.common_ui.R
import com.nafepay.common_ui.events.CommonEvent
import com.nafepay.common_ui.utils.BottomSheetOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun moreHeaderIcon(
    tintColor : Color =   MaterialTheme.colorScheme.surface,
    onAction :  ()  -> Unit){
    Image(
            painterResource(R.drawable.ic_more_vertical),
            "",
            modifier = Modifier
                .padding(
                    horizontal = 10.dp,
                )
                .clickable {
                    onAction()
                },
            colorFilter = ColorFilter.tint(tintColor)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
fun bottomSheetAction(
    scope: CoroutineScope,
    sheetState: SheetState,
    bottomOptions: List<BottomSheetOption>,
    events: (event: CommonEvent) -> Unit
) {
    scope.launch {
        if (sheetState.currentValue == SheetValue.Hidden) {
            sheetState.show() // expands the sheet
            events(CommonEvent.GetBottomSheetOptions(bottomOptions))
        } else {
            sheetState.hide() // collapses / hides the sheet
        }
    }
}


fun shareProfile(share : String,context: Context){
    val sendIntent = Intent().apply{
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, share)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent,null)

    context.startActivity(shareIntent)
}


//fun shareSong(song : Song, context: Context){
//    val text = "${song.songName} by ${song.artistName}"
//    val pictureUri = Uri.parse(song.artwork)
//    val shareIntent = Intent()
//    shareIntent.action = Intent.ACTION_SEND
//    shareIntent.putExtra(Intent.EXTRA_TEXT, text)
//    shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri)
//    shareIntent.type = "image/*"
//    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//    context.startActivity(Intent.createChooser(shareIntent, "Share Song"))
//}

fun shareImages(imageUris: ArrayList<Uri>, context: Context){


    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND_MULTIPLE
        putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        type = "image/*"
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share images to.."))
}

fun copyToClipboard(textToCopy: String,context: Context){

    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("PROFILE",textToCopy)
    clipboard.setPrimaryClip(clip)
}

val SELF_PROFILE_OPTIONS = listOf(
    BottomSheetOption(null,"Share Profile",{}),
    BottomSheetOption(null,"Copy Profile URL",{}),
    BottomSheetOption(null,"Settings",{})
)