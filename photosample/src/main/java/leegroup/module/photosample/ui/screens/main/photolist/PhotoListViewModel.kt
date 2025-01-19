package leegroup.module.photosample.ui.screens.main.photolist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.models.ErrorState
import leegroup.module.compose.ui.viewmodel.BaseViewModel
import leegroup.module.extension.orFalse
import leegroup.module.photosample.domain.models.PhotoModelD
import leegroup.module.photosample.domain.usecases.photofavorite.ObserveFavoriteListUseCase
import leegroup.module.photosample.domain.usecases.photofavorite.SaveFavoriteUseCase
import leegroup.module.photosample.domain.usecases.photolist.GetPhotoListFavoriteFilterUseCase
import leegroup.module.photosample.domain.usecases.photolist.GetPhotoListUseCase
import leegroup.module.photosample.domain.usecases.photolist.SavePhotoListFavoriteFilterUseCase
import leegroup.module.photosample.ui.models.PhotoListUiModel
import leegroup.module.photosample.ui.models.PhotoUiModel
import leegroup.module.photosample.ui.models.createSaveParam
import leegroup.module.photosample.ui.models.mapToUiModels
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@Suppress("TooManyFunctions")
internal class PhotoListViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val getPhotoListUseCase: GetPhotoListUseCase,
    private val observeFavoriteListUseCase: ObserveFavoriteListUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteFilterUseCase: GetPhotoListFavoriteFilterUseCase,
    private val saveFavoriteFilterUseCase: SavePhotoListFavoriteFilterUseCase
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow(PhotoListUiModel())
    val uiModel = _uiModel.asStateFlow()

    private var loadDataJob: Job? = null

    init {
        getFavoriteFilter()
        observeFavoriteList()
    }

    fun handleAction(action: PhotoListAction) {
        when (action) {
            is PhotoListAction.LoadIfEmpty -> loadIfEmpty()
            is PhotoListAction.LoadMore -> loadMore()
            is PhotoListAction.FavoriteFilterClick -> handleFavoriteFilterClick()
            is PhotoListAction.Query -> handleQueryChanged(action)
            is PhotoListAction.FavoriteItemClick -> handleFavoriteClick(action.item)
            is PhotoListAction.PhotoClick -> _navigator.tryEmit(action.item)
        }
    }

    private fun observeFavoriteList() {
        observeFavoriteListUseCase.invoke()
            .onEach { favoriteList ->
                _uiModel.update { oldValue ->
                    oldValue.updateFavorites(favoriteList)
                }
            }
            .flowOn(dispatchersProvider.io)
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    private fun getFavoriteFilter() {
        getFavoriteFilterUseCase.invoke().take(1)
            .onEach { isEnabled ->
                _uiModel.update { uiState ->
                    uiState.copy(isFavoriteEnabled = isEnabled)
                }
            }.flowOn(dispatchersProvider.io)
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    private fun handleFavoriteFilterClick() {
        cancelCurrentLoading()

        val newUiModel = _uiModel.value.switchFavoriteFilter().resetData()
        _uiModel.update { newUiModel }

        saveFavoriteFilterToLocal(newUiModel.isFavoriteEnabled)
        loadMore()
    }

    private fun handleFavoriteClick(item: PhotoUiModel) {
        viewModelScope.launch(dispatchersProvider.io) {
            val newItem = item.switchFavorite()
            saveFavoriteUseCase.invoke(newItem.createSaveParam())
        }
    }

    private fun handleQueryChanged(query: PhotoListAction.Query) {
        if (query.query == _uiModel.value.query) return

        cancelCurrentLoading()
        _uiModel.update { uiState ->
            uiState
                .resetData()
                .updateQuery(query.query)
        }
        loadMore()
    }

    private fun loadIfEmpty() {
        if (isEmpty()) {
            loadMore()
        }
    }

    private fun loadMore() {
        if (canLoadMore().not()) return

        val currentState = _uiModel.value
        loadDataJob = viewModelScope.launch(dispatchersProvider.io) {
            showLoading()
            currentState.delayQuery()
            getPhotoListUseCase(currentState.createLoadMoreQueryParam()).injectLoading()
                .onEach { result ->
                    onLoadMoreSuccess(result)
                }.flowOn(dispatchersProvider.io).catch { e ->
                    handleError(e)
                }.launchIn(viewModelScope).join()
        }
    }

    private fun onLoadMoreSuccess(result: List<PhotoModelD>) {
        _uiModel.update { uiState ->
            uiState
                .plusPhotos(result.mapToUiModels())
                .copy(hasMore = result.isNotEmpty())
        }
    }

    private fun cancelCurrentLoading() {
        loadDataJob?.cancel()
    }

    private fun saveFavoriteFilterToLocal(newFavoriteFilter: Boolean) {
        viewModelScope.launch(dispatchersProvider.io) {
            saveFavoriteFilterUseCase.invoke(newFavoriteFilter)
        }
    }

    private fun canLoadMore(): Boolean {
        return _uiModel.value.canLoadMore() && isLoadingJobActive().not()
    }

    private fun isLoadingJobActive() = loadDataJob?.isActive.orFalse

    override fun onErrorConfirmation(errorState: ErrorState) {
        super.onErrorConfirmation(errorState)
        when (errorState) {
            is ErrorState.Api, is ErrorState.Network -> loadMore()
            else -> Unit
        }
    }
}
