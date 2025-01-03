package com.app.androidcompose.ui.screens.main.gituserdetail

import androidx.lifecycle.viewModelScope
import com.app.androidcompose.support.util.DispatchersProvider
import com.app.androidcompose.ui.base.BaseViewModel
import com.app.androidcompose.ui.base.ErrorState
import com.app.androidcompose.ui.mapper.GitUserDetailUiMapper
import com.app.androidcompose.ui.models.GitUserDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import leegroup.module.domain.models.GitUserDetailModel
import leegroup.module.domain.usecases.gituser.GetGitUserDetailLocalUseCase
import leegroup.module.domain.usecases.gituser.GetGitUserDetailRemoteUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GitUserDetailViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getGitUserDetailLocalUseCase: GetGitUserDetailLocalUseCase,
    private val getGitUserDetailRemoteUseCase: GetGitUserDetailRemoteUseCase,
    private val gitUserDetailModelMapper: GitUserDetailUiMapper,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow(GitUserDetailUiModel())
    val uiModel = _uiModel.asStateFlow()

    fun handleAction(action: GitUserDetailAction) {
        when (action) {
            is GitUserDetailAction.SetUserLogin -> setUserLogin(action.login)
        }
    }

    private fun getLocal() {
        getGitUserDetailLocalUseCase(getLogin())
            .onEach { result ->
                handleSuccess(result)
            }
            .onEach {
                hideLoading() // Hide loading if local data is available
            }
            .flowOn(dispatchersProvider.io)
            .catch {
                Timber.e(it) // Not show local error to screen
            }
            .launchIn(viewModelScope)
    }

    private fun fetchRemote() {
        getGitUserDetailRemoteUseCase(getLogin())
            .let {
                // Show loading if data is empty
                when {
                    isDataEmpty() -> it.injectLoading()
                    else -> it
                }
            }
            .onEach { result ->
                handleSuccess(result)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e ->
                if (isDataEmpty()) {
                    handleError(e)  // Show error if data is empty
                } else {
                    Timber.e(e)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleSuccess(result: GitUserDetailModel) {
        _uiModel.update { oldValue ->
            gitUserDetailModelMapper.mapToUiModel(oldValue, result)
        }
    }

    private fun setUserLogin(login: String) {
        _uiModel.update { oldValue -> oldValue.copy(login = login) }
        getLocal()
        fetchRemote()
    }

    private fun isDataEmpty(): Boolean {
        return _uiModel.value.name.isBlank()
    }

    private fun getLogin() = _uiModel.value.login

    override fun onErrorConfirmation(errorState: ErrorState) {
        super.onErrorConfirmation(errorState)
        when (errorState) {
            is ErrorState.Api, is ErrorState.Network -> fetchRemote()
            else -> Unit
        }
    }
}
