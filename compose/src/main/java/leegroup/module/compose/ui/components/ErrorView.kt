package leegroup.module.compose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import leegroup.module.compose.ui.models.ErrorState

@Composable
fun ErrorView(
    error: ErrorState,
    onErrorConfirmation: (ErrorState) -> Unit = {},
    onErrorDismissRequest: (ErrorState) -> Unit = {},
) {
    when (error) {
        is ErrorState.MessageError -> {
            val message = when (error) {
                is ErrorState.Api -> error.customMessage ?: stringResource(error.messageRes)
                else -> stringResource(error.messageRes)
            }
            AlertDialogView(
                title = stringResource(id = error.titleRes),
                text = message,
                confirmText = stringResource(id = error.primaryRes),
                dismissText = error.secondaryRes?.let { stringResource(id = it) },
                onConfirmation = { onErrorConfirmation(error) },
                onDismissRequest = { onErrorDismissRequest(error) })
        }

        is ErrorState.None -> {}
    }
}

