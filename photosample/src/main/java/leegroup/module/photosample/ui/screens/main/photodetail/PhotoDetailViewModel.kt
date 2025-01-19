package leegroup.module.photosample.ui.screens.main.photodetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import leegroup.module.compose.support.extensions.toNavModel
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.viewmodel.StateViewModel
import leegroup.module.photosample.domain.params.SaveFavoriteParam
import leegroup.module.photosample.domain.usecases.photofavorite.SaveFavoriteUseCase
import leegroup.module.photosample.ui.models.PhotoDetailUiModel
import leegroup.module.photosample.ui.screens.main.PhotoDetailNav
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class PhotoDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatchersProvider: DispatchersProvider,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
) : StateViewModel<PhotoDetailUiModel>(PhotoDetailUiModel()) {

    init {
        loadFromSavedStateHandle()
    }

    fun switchFavorite() {
        viewModelScope.launch(dispatchersProvider.io) {
            runCatching {
                val newModel = getUiState().reverseFavorite()
                saveFavoriteUseCase.invoke(
                    SaveFavoriteParam(
                        id = newModel.id,
                        isFavorite = newModel.isFavorite
                    )
                )
                update { newModel }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private fun loadFromSavedStateHandle() {
        val navModel = savedStateHandle.toNavModel<PhotoDetailNav>()
        update { oldValue ->
            oldValue.update(navModel)
        }
    }

}
