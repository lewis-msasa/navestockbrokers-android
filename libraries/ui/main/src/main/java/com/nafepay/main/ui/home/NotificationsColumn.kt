package com.nafepay.main.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nafepay.authentication.models.Notification
import com.nafepay.common_ui.composers.sections.notificationsSingleRow
import com.nafepay.common_ui.theme.NafeTheme
import com.nafepay.common_ui.theme.commonPadding
import com.nafepay.domain.utils.NotificationType


@Preview(showBackground = true)
@Composable
fun prevNotificationsColumn() {
    NafeTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

            val notifications = listOf(
                Notification(
                    "Lewis Msasa",
                    "12345",
                    "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                    "Started following you",
                    "2h",
                    NotificationType.FOLLOW.type
                ),
                Notification(
                    "Lewis Msasa Jnr",
                    "123",
                    "https://www.pngkit.com/png/detail/115-1150342_user-avatar-icon-iconos-de-mujeres-a-color.png",
                    "Started following you",
                    "2h",
                   NotificationType.FOLLOW.type
                )
            );
            notificationsColumn(notifications, "2 Hours ago");
        }
    }
}
@Composable
fun notificationsColumn(notifications : List<Notification>, friendlyTime : String){
      Column(
          modifier = Modifier.padding(horizontal = commonPadding)
      ) {
          Text(
              friendlyTime,
              textAlign = TextAlign.Start,
              style = MaterialTheme.typography.titleSmall.copy(
                  MaterialTheme.colorScheme.onSurface,
                  fontWeight = FontWeight.Bold,
              ),
          )
          Spacer(modifier = Modifier.height(20.dp))
          notifications.forEach{ notification ->
              notificationsSingleRow(
                  100.dp,
                  notification.notifier,
                  notification.notifierImgUrl,
                  notification.notification,
                  notification.friendlyTime
              ){
                  Button(
                      onClick = { /*TODO*/ },
                      shape = RoundedCornerShape(10),
                  ) {
                      Text("Follow", color = Color.White)
                  }
              }
          }


      }
}