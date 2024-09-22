package com.app.androidcompose.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import com.app.androidcompose.data.model.User
import com.app.androidcompose.domain.usecases.user.GetUserRemoteUseCase
import com.app.androidcompose.ui.base.BaseViewModel
import com.app.androidcompose.support.util.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    useCase: GetUserRemoteUseCase,
) : BaseViewModel() {

    private val _uiModels = MutableStateFlow<List<User>>(emptyList())
    val uiModels = _uiModels.asStateFlow()

    init {
        useCase()
            .retry(1)
            .injectLoading()
            .onEach { result ->
                _uiModels.emit(result)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e ->
                handleError(e)
            }
            .launchIn(viewModelScope)
    }
}
