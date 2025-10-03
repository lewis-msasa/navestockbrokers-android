package com.nafepay.main.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.nafepay.common_ui.composers.general.WebView
import com.nafepay.common_ui.viewModels.CommonViewModel


@Composable
fun WebViewScreen(
    commonViewModel: CommonViewModel
){
    val state by commonViewModel.uiState.collectAsState()
    WebView(url = state.webUrl)
}