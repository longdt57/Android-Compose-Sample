package com.app.androidcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.androidcompose.ui.base.composable.AlertDialogView
import com.app.androidcompose.ui.base.composable.LoadingProgress

@Composable
fun BaseScreen(
    viewModel: BaseViewModel? = null,
    onErrorConfirmation: (ErrorState) -> Unit = {},
    onErrorDismissRequest: (ErrorState) -> Unit = {},
    content: @Composable () -> Unit,
) {
    content()
    if (viewModel != null) {
        LoadingView(loading = viewModel.loading.collectAsStateWithLifecycle().value)
        ErrorView(
            error = viewModel.error.collectAsStateWithLifecycle().value,
            onErrorConfirmation = {
                viewModel.hideError()
                onErrorConfirmation(it)
            },
            onErrorDismissRequest = {
                viewModel.hideError()
                onErrorDismissRequest(it)
            }
        )
    }

}

@Composable
private fun LoadingView(loading: LoadingState) {
    when (loading) {
        is LoadingState.Loading -> {
            LoadingProgress(loading)
        }

        else -> {}
    }
}

@Composable
private fun ErrorView(
    error: ErrorState,
    onErrorConfirmation: (ErrorState) -> Unit = {},
    onErrorDismissRequest: (ErrorState) -> Unit = {},
) {
    when (error) {
        is ErrorState.MessageError -> {
            AlertDialogView(
                dialogTitle = error.titleRes?.let { stringResource(id = it) },
                dialogText = error.messageRes?.let { stringResource(id = it) },
                confirmText = error.primaryRes?.let { stringResource(id = it) },
                dismissText = error.secondaryRes?.let { stringResource(id = it) },
                onConfirmation = { onErrorConfirmation(error) },
                onDismissRequest = { onErrorDismissRequest(error) })
        }

        is ErrorState.None -> {}
    }
}
