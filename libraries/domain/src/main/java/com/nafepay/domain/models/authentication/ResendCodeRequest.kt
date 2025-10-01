package com.nafepay.domain.models.authentication

import kotlinx.serialization.Serializable

@Serializable
data class ResendCodeRequest(
    val userId : String
)