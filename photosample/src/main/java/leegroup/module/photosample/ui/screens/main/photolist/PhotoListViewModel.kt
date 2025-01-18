package leegroup.module.photosample.ui.screens.main.photolist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
import leegroup.module.photosample.domain.params.GetPhotoListParam
import leegroup.module.photosample.domain.params.SaveFavoriteParam
import leegroup.module.photosample.domain.usecases.photofavorite.ObserveFavoriteListUseCase
import leegroup.module.photosample.domain.usecases.photofavorite.SaveFavoriteUseCase
import leegroup.module.photosample.domain.usecases.photolist.GetPhotoListFavoriteFilterUseCase
import leegroup.module.photosample.domain.usecases.photolist.GetPhotoListUseCase
import leegroup.module.photosample.domain.usecases.photolist.SavePhotoListFavoriteFilterUseCase
import leegroup.module.photosample.ui.models.PhotoListUiModel
import leegroup.module.photosample.ui.models.PhotoUiModel
import leegroup.module.photosample.ui.models.mapToUiModels
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
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

    private fun observeFavoriteList() {
        observeFavoriteListUseCase.invoke()
            .onEach {
                updateFavoriteList(it)
            }
            .flowOn(dispatchersProvider.io)
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    private fun getFavoriteFilter() {
        getFavoriteFilterUseCase.invoke()
            .take(1)
            .onEach { isEnabled ->
                updateFavoriteFilter(isEnabled)
            }
            .flowOn(dispatchersProvider.io)
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    fun handleAction(action: PhotoListAction) {
        when (action) {
            is PhotoListAction.LoadIfEmpty -> loadIfEmpty()
            is PhotoListAction.LoadMore -> loadMore()
            is PhotoListAction.FavoriteFilterClick -> onFavoriteFilterClick()
            is PhotoListAction.Query -> updateQuery(action)
            is PhotoListAction.FavoriteItemClick -> handleFavoriteClick(action.item)
            is PhotoListAction.PhotoClick -> handlePhotoClick(action.item)
        }
    }

    private fun onFavoriteFilterClick() {
        val newFavoriteFilter = _uiModel.value.isFavoriteEnabled.not()
        cancelCurrentLoading()
        updateFavoriteFilter(newFavoriteFilter)
        saveFavoriteFilterToLocal(newFavoriteFilter)
        resetListData()
        loadMore()
    }

    private fun handleFavoriteClick(item: PhotoUiModel) {
        viewModelScope.launch(dispatchersProvider.io) {
            saveFavoriteUseCase.invoke(
                SaveFavoriteParam(
                    id = item.id,
                    isFavorite = item.isFavorite.not()
                )
            )
        }
    }

    private fun handlePhotoClick(item: PhotoUiModel) {
        _navigator.tryEmit(item)
    }

    private fun updateQuery(query: PhotoListAction.Query) {
        if (query.query == _uiModel.value.query) return

        cancelCurrentLoading()
        resetListData()
        updateUiStateQuery(query)
        loadMore()
    }

    private fun resetListData() {
        _uiModel.update { oldValue ->
            oldValue.copy(photos = persistentListOf(), hasMore = true)
        }
    }

    private fun loadIfEmpty() {
        if (isEmpty()) {
            loadMore()
        }
    }

    private fun loadMore() {
        if (loadDataJob?.isActive.orFalse) return

        val currentState = _uiModel.value
        loadDataJob = viewModelScope.launch(dispatchersProvider.io) {
            showLoading()
            delayQuery(currentState)
            getPhotoListUseCase(createRequestParam())
                .injectLoading()
                .onEach { result ->
                    handleSuccess(result)
                }
                .flowOn(dispatchersProvider.io)
                .catch { e ->
                    handleError(e)
                }
                .launchIn(viewModelScope)
                .join()
        }
    }

    override fun onErrorConfirmation(errorState: ErrorState) {
        super.onErrorConfirmation(errorState)
        when (errorState) {
            is ErrorState.Api, is ErrorState.Network -> loadMore()
            else -> Unit
        }
    }

    private fun handleSuccess(result: List<PhotoModelD>) {
        _uiModel.update { oldValue ->
            oldValue
                .plusPhotos(result.mapToUiModels())
                .copy(hasMore = result.isNotEmpty())
        }
    }

    private fun isEmpty() = _uiModel.value.photos.isEmpty()

    private fun updateFavoriteList(favoriteList: Set<Int>) {
        _uiModel.update { oldValue ->
            oldValue.updateFavorites(favoriteList)
        }
    }

    private fun cancelCurrentLoading() {
        loadDataJob?.cancel()
    }

    private fun updateFavoriteFilter(isEnabled: Boolean) {
        _uiModel.update { oldValue ->
            oldValue.copy(isFavoriteEnabled = isEnabled)
        }
    }

    private fun saveFavoriteFilterToLocal(newFavoriteFilter: Boolean) {
        viewModelScope.launch(dispatchersProvider.io) {
            saveFavoriteFilterUseCase.invoke(newFavoriteFilter)
        }
    }

    private fun updateUiStateQuery(query: PhotoListAction.Query) {
        _uiModel.update { oldValue ->
            oldValue.copy(query = query.query, delayQuery = query.delay)
        }
    }

    private fun createRequestParam(): GetPhotoListParam {
        return GetPhotoListParam(
            ids = getParamQueryIds(),
            titleLike = _uiModel.value.query,
            since = getParamQueryIdSince(),
            limit = PER_PAGE,
        )
    }

    private fun getParamQueryIds() = with(_uiModel.value) {
        if (isFavoriteEnabled) favoriteList.toList() else null
    }

    private fun getParamQueryIdSince() = _uiModel.value.photos.lastOrNull()?.id ?: 0

    private suspend fun delayQuery(state: PhotoListUiModel) {
        if (state.delayQuery > 0) {
            delay(state.delayQuery)
        }
    }

    companion object {
        const val PER_PAGE = 30
    }
}
