package com.app.androidcompose.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.exceptions.NoConnectivityException
import leegroup.module.domain.exceptions.ServerException

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    private val _loading: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
    val loading = _loading.asStateFlow()

    protected val _error = MutableStateFlow<ErrorState>(ErrorState.None)
    val error = _error.asStateFlow()

    protected val _navigator = MutableSharedFlow<BaseDestination>()
    val navigator = _navigator.asSharedFlow()

    protected open fun showLoading() {
        _loading.value = LoadingState.Loading()
    }

    protected open fun hideLoading() {
        _loading.value = LoadingState.None
    }

    protected open fun handleError(e: Throwable) {
        val error = when (e) {
            is NoConnectivityException -> ErrorState.Network()
            is ServerException -> ErrorState.Server()
            is ApiException -> ErrorState.Api()
            else -> ErrorState.Network()
        }
        _error.tryEmit(error)
    }

    fun hideError() {
        _error.tryEmit(ErrorState.None)
    }

    protected fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
}
