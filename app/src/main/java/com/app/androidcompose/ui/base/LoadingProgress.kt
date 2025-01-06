package com.app.androidcompose.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.androidcompose.R
import com.app.androidcompose.ui.theme.ComposeTheme

@Composable
fun LoadingProgress(loading: LoadingState.Loading) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            loading.message().takeUnless { it.isNullOrBlank() }?.let {
                Text(modifier = Modifier.padding(8.dp), text = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingProgressPreview() {
    ComposeTheme {
        LoadingProgress(loading = LoadingState.Loading(message = {
            stringResource(R.string.loading)
        }))
    }
}