package com.app.androidcompose.ui.base

import androidx.compose.runtime.Composable

@Composable
fun BaseScreen(
    viewModel: BaseViewModel,
    content: @Composable () -> Unit,
) {
    content()
    LoadingView(viewModel)
    ErrorView(viewModel)
}
