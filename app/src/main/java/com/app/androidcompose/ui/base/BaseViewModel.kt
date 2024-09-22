package com.app.androidcompose.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.androidcompose.domain.exceptions.ApiException
import com.app.androidcompose.domain.exceptions.NoConnectivityException
import com.app.androidcompose.domain.exceptions.ServerException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("PropertyName")
abstract class BaseViewModel : ViewModel() {

    private val _loading: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
    val loading = _loading.asStateFlow()

    protected val _error = MutableStateFlow<ErrorState>(ErrorState.None)
    val error = _error.asStateFlow()

    protected val _navigator = MutableSharedFlow<BaseDestination>()
    val navigator = _navigator.asSharedFlow()

    protected fun showLoading() {
        _loading.value = LoadingState.Loading()
    }

    protected fun hideLoading() {
        _loading.value = LoadingState.None
    }

    protected fun handleError(e: Throwable) {
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

    protected fun launch(context: CoroutineContext = EmptyCoroutineContext, job: suspend () -> Unit) =
        viewModelScope.launch(context) {
            job.invoke()
        }

    protected fun <T> Flow<T>.injectLoading(): Flow<T> = this
        .onStart { showLoading() }
        .onCompletion { hideLoading() }
}
