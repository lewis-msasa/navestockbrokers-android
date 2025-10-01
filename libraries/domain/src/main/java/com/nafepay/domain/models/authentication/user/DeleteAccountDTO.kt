package com.nafepay.domain.models.authentication.user

data class DeleteAccountDTO(
    val  userId: String,
    val  reason: String,
    val  password:  String
)
