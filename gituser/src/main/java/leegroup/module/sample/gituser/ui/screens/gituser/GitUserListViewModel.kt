package leegroup.module.sample.gituser.ui.screens.gituser

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.models.ErrorState
import leegroup.module.compose.ui.viewmodel.BaseViewModel
import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.params.GetGitUserListParam
import leegroup.module.domain.usecases.gituser.GetGitUserUseCase
import leegroup.module.sample.gituser.tracking.GitUserListScreenTracker
import leegroup.module.sample.gituser.ui.models.GitUserListUiModel
import javax.inject.Inject

@HiltViewModel
class GitUserListViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val useCase: GetGitUserUseCase,
    private val gitUserListScreenTracker: GitUserListScreenTracker,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow(GitUserListUiModel())
    val uiModel = _uiModel.asStateFlow()

    fun handleAction(action: GitUserListAction) {
        when (action) {
            is GitUserListAction.LoadIfEmpty -> loadIfEmpty()
            is GitUserListAction.LoadMore -> loadMore()
            is GitUserListAction.TrackLaunch -> trackLaunch()
            is GitUserListAction.TrackOpenUserDetail -> trackOpenUserDetail(action)
        }
    }

    private fun loadIfEmpty() {
        if (isEmpty()) {
            loadMore()
        }
    }

    private fun loadMore() {
        if (isLoading()) return
        val param = GetGitUserListParam(getSince(), PER_PAGE)
        useCase(param)
            .injectLoading()
            .onEach { result ->
                handleSuccess(result)
            }
            .flowOn(dispatchersProvider.io)
            .catch { e ->
                handleError(e)
            }
            .launchIn(viewModelScope)
    }

    override fun onErrorConfirmation(errorState: ErrorState) {
        super.onErrorConfirmation(errorState)
        when (errorState) {
            is ErrorState.Api, is ErrorState.Network -> loadMore()
            else -> Unit
        }
    }

    private fun handleSuccess(result: List<GitUserModel>) {
        _uiModel.update { oldValue ->
            val users = oldValue.users.plus(result).toSet().toImmutableList()
            oldValue.copy(users = users)
        }
    }

    private fun isEmpty() = _uiModel.value.users.isEmpty()

    private fun getSince() = _uiModel.value.users.lastOrNull()?.id ?: 0L

    private fun trackLaunch() {
        viewModelScope.launch(dispatchersProvider.io) {
            gitUserListScreenTracker.launch()
        }
    }


    private fun trackOpenUserDetail(action: GitUserListAction.TrackOpenUserDetail) {
        viewModelScope.launch(dispatchersProvider.io) {
            gitUserListScreenTracker.openUserDetail(action.login)
        }
    }

    companion object {
        const val PER_PAGE = 20
    }
}
