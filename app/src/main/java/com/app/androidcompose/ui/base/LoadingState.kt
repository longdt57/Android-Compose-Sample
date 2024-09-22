package com.app.androidcompose.ui.base

import androidx.annotation.StringRes

sealed interface LoadingState {
    data object None : LoadingState
    data class Loading(val cancelable: Boolean = true, @StringRes val message: Int = -1) : LoadingState
}