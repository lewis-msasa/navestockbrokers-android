package com.nafepay.domain.models.notifications


import com.nafepay.domain.models.authentication.user.UserDTO
import kotlinx.serialization.Serializable


@Serializable
data class Notification(
    val id: Long,
    val dateCreated: String,
    val type: String,
    val subject: String,
    val description: String,
    val read: Boolean,
    val notifier: UserDTO,
    val notified: UserDTO
)
