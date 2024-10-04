package com.app.androidcompose.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.androidcompose.support.extensions.takeIfValidRes

@Composable
fun LoadingProgress(loading: LoadingState.Loading) {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = loading.cancelable, dismissOnClickOutside = loading.cancelable)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
        ) {
            CircularProgressIndicator()
            loading.message.takeIfValidRes()?.let {
                val text = stringResource(id = it)
                Text(text = text)
            }
        }
    }
}

@Preview
@Composable
private fun LoadingProgressPreview() {
    LoadingProgress(loading = LoadingState.Loading())
}