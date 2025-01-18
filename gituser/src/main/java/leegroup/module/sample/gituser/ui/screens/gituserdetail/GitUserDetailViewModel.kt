package leegroup.module.sample.gituser.ui.screens.gituserdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import leegroup.module.compose.support.extensions.toNavModel
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.models.ErrorState
import leegroup.module.compose.ui.viewmodel.BaseViewModel
import leegroup.module.sample.gituser.domain.models.GitUserDetailModel
import leegroup.module.sample.gituser.domain.usecases.gituser.GetGitUserDetailLocalUseCase
import leegroup.module.sample.gituser.domain.usecases.gituser.GetGitUserDetailRemoteUseCase
import leegroup.module.sample.gituser.ui.mapper.GitUserDetailUiMapper
import leegroup.module.sample.gituser.ui.models.GitUserDetailUiModel
import leegroup.module.sample.gituser.ui.screens.GitUserDestination
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GitUserDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatchersProvider: DispatchersProvider,
    private val getGitUserDetailLocalUseCase: GetGitUserDetailLocalUseCase,
    private val getGitUserDetailRemoteUseCase: GetGitUserDetailRemoteUseCase,
    private val gitUserDetailModelMapper: GitUserDetailUiMapper,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow(GitUserDetailUiModel())
    val uiModel = _uiModel.asStateFlow()

    init {
        loadFromSavedStateHandle()
    }

    private fun loadFromSavedStateHandle() {
        val navModel =
            savedStateHandle.toNavModel<GitUserDestination.GitUserDetail.GitUserDetailNav>()
        setUserLogin(navModel.login)
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
