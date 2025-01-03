package com.app.androidcompose.ui.base

import androidx.annotation.StringRes
import com.app.androidcompose.R

sealed interface LoadingState {
    data object None : LoadingState
    data class Loading(@StringRes val messageRes: Int = R.string.loading) : LoadingState
}