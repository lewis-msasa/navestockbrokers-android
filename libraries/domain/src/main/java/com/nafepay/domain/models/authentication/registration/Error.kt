package com.nafepay.domain.models.authentication.registration

import kotlinx.serialization.Serializable


@Serializable
data class Error (

    val StatusCode : String,
    val Message : String
)


