package com.app.androidcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ErrorView(viewModel: BaseViewModel) {
    val error by viewModel.error.collectAsStateWithLifecycle()
    ErrorView(
        error = error,
        onErrorConfirmation = { viewModel.onErrorConfirmation(it) },
        onErrorDismissRequest = { viewModel.onErrorDismissClick(it) }
    )
}

@Composable
fun ErrorView(
    error: ErrorState,
    onErrorConfirmation: (ErrorState) -> Unit = {},
    onErrorDismissRequest: (ErrorState) -> Unit = {},
) {
    when (error) {
        is ErrorState.MessageError -> {
            val message = when (error) {
                is ErrorState.Api -> error.customMessage ?: error.message()
                else -> error.message()
            }
            AlertDialogView(
                icon = error.icon,
                title = error.title(),
                text = message,
                confirmText = error.confirmText(),
                dismissText = error.dismissText?.invoke(),
                onConfirmation = { onErrorConfirmation(error) },
                onDismissRequest = { onErrorDismissRequest(error) })
        }

        is ErrorState.None -> {}
    }
}
