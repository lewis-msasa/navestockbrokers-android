package com.nafepay.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GeneralResult(
    val success : Boolean,
    val message : String
)