package com.app.androidcompose.ui.screens.main.home

import androidx.lifecycle.viewModelScope
import com.app.androidcompose.support.util.DispatchersProvider
import com.app.androidcompose.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import leegroup.module.domain.usecases.user.GetUserRemoteUseCase

@HiltViewModel
class HomeViewModel @Inject constructor(
    dispatchersProvider: DispatchersProvider,
    useCase: GetUserRemoteUseCase,
) : BaseViewModel() {

    private val _uiModels = MutableStateFlow(HomeUiModel())
    val uiModels = _uiModels.asStateFlow()

    init {
        useCase()
            .injectLoading()
            .onEach { result ->
                _uiModels.update { it.copy(users = result) }
            }
            .flowOn(dispatchersProvider.io)
            .catch { e ->
                handleError(e)
            }
            .launchIn(viewModelScope)
    }
}
