package com.nafepay.domain.models.authentication.user

data class DisableAccountDTO(
    val  userId: String,
    val  reason: String,
    val  password:  String
)
