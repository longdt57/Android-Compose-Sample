package com.app.androidcompose.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.app.androidcompose.R

sealed interface LoadingState {
    data object None : LoadingState
    data class Loading(
        val message: @Composable () -> String = {
            stringResource(R.string.loading)
        }
    ) : LoadingState
}