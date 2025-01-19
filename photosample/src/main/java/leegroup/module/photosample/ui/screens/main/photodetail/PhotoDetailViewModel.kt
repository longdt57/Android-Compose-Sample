package leegroup.module.photosample.ui.screens.main.photodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import leegroup.module.compose.support.extensions.toNavModel
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.viewmodel.BaseViewModel
import leegroup.module.photosample.domain.usecases.photofavorite.SaveFavoriteUseCase
import leegroup.module.photosample.ui.models.PhotoDetailUiModel
import leegroup.module.photosample.ui.models.createSaveParam
import leegroup.module.photosample.ui.screens.main.PhotoDetailNav
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatchersProvider: DispatchersProvider,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
) : BaseViewModel() {

    private val _uiModel = MutableStateFlow(PhotoDetailUiModel())
    val uiModel = _uiModel.asStateFlow()

    init {
        loadFromSavedStateHandle()
    }

    fun handleAction(action: PhotoDetailAction) {
        when (action) {
            PhotoDetailAction.SwitchFavorite -> switchFavorite()
        }
    }

    private fun switchFavorite() {
        viewModelScope.launch(dispatchersProvider.io) {
            runCatching {
                val newModel = _uiModel.value.reverseFavorite()
                saveFavoriteUseCase.invoke(newModel.createSaveParam())
                _uiModel.update { newModel }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun loadFromSavedStateHandle() {
        val navModel = savedStateHandle.toNavModel<PhotoDetailNav>()
        _uiModel.update { oldValue ->
            oldValue.update(navModel)
        }
    }
}
