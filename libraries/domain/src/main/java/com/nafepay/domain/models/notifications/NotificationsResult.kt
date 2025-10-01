package com.nafepay.domain.models.notifications
import kotlinx.serialization.Serializable


@Serializable
data class NotificationsResult(
    val notifications : List<Notification> = listOf()
)
