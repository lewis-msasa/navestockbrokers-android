package com.nafepay.authentication.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.nafepay.core.utils.Utils


data class Notification(
    val notifier : String,
    val notifierId : String,
    val notifierImgUrl : String,
    val notification : String,
    val friendlyTime : String,
    val notificationType : String,
){
    object ModelMapper {
        @RequiresApi(Build.VERSION_CODES.O)
        fun from(notification: com.nafepay.domain.models.notifications.Notification) =
            Notification(
                notifier =  notification.notifier.fullName,
                notifierId = notification.notifier.id,
                notifierImgUrl = "",
                notification = notification.description,
                friendlyTime = Utils.getPrettyTime(notification.dateCreated),
                notificationType = notification.type//NotificationType.FOLLOW.type
            )
    }
}