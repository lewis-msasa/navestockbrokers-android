package com.nafepay.domain.exceptions

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralError (

    @SerializedName("error_description") val errorDescription : String,
    @SerializedName("details") val details : String
)