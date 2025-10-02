package com.nafepay.common_ui.utils

data class BottomSheetOption (
    val icon : Int?  = null,
    val text : String,
    val action : () -> Unit
)