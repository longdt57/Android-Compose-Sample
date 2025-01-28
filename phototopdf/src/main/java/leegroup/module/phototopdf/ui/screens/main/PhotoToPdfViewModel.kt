package leegroup.module.phototopdf.ui.screens.main

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import leegroup.module.compose.support.util.DispatchersProvider
import leegroup.module.compose.ui.viewmodel.StateViewModel
import leegroup.module.phototopdf.data.models.PdfFile
import leegroup.module.phototopdf.domain.usecases.ConvertPhotoToPdfUseCase
import leegroup.module.phototopdf.domain.usecases.DeleteFileUseCase
import leegroup.module.phototopdf.domain.usecases.GetPdfFilesUseCase
import leegroup.module.phototopdf.domain.usecases.SaveFileToDocumentUseCase
import leegroup.module.phototopdf.ui.mapper.PdfModelMapper
import leegroup.module.phototopdf.ui.models.PdfUiModel
import leegroup.module.phototopdf.ui.models.PhotoToPdfScreenUiModel
import javax.inject.Inject

@HiltViewModel
internal class PhotoToPdfViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val convertPhotoToPdfUseCase: ConvertPhotoToPdfUseCase,
    private val getPdfFilesUseCase: GetPdfFilesUseCase,
    private val deletePdfUseCase: DeleteFileUseCase,
    private val saveFileToDocumentUseCase: SaveFileToDocumentUseCase,

    // Mapper
    private val pdfModelMapper: PdfModelMapper,
) : StateViewModel<PhotoToPdfScreenUiModel>(PhotoToPdfScreenUiModel()) {

    private val _saveSnackBarFlow = MutableSharedFlow<Unit>(replay = 0, extraBufferCapacity = 1)
    val saveSnackBarFlow = _saveSnackBarFlow.asSharedFlow()

    init {
        getPdfFiles()
    }

    fun convertToPdf(uri: Uri) {
        convertPhotoToPdfUseCase(uri)
            .injectLoading()
            .filterNotNull()
            .onEach { pdf ->
                update { state ->
                    state.addToStart(pdfModelMapper.mapToPdfUiModel(pdf))
                }
            }
            .flowOn(dispatchersProvider.io)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    fun getPdfFiles() {
        getPdfFilesUseCase()
            .injectLoading()
            .onEach { handleGetPdfFilesSuccess(it) }
            .flowOn(dispatchersProvider.io)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    fun delete(item: PdfUiModel) {
        deletePdfUseCase.invoke(item.uri)
            .injectLoading()
            .filter { it }
            .onEach {
                update { state -> state.removeItem(item) }
            }
            .flowOn(dispatchersProvider.io)
            .catch { handleError(it) }
            .launchIn(viewModelScope)

    }

    fun saveToDocuments(item: PdfUiModel) {
        saveFileToDocumentUseCase.invoke(item.uri)
            .injectLoading()
            .onEach {
                _saveSnackBarFlow.tryEmit(Unit)
            }
            .flowOn(dispatchersProvider.io)
            .catch { handleError(it) }
            .launchIn(viewModelScope)
    }

    private fun handleGetPdfFilesSuccess(pdfFiles: List<PdfFile>) {
        update {
            it.copy(
                items = pdfFiles
                    .map { item -> pdfModelMapper.mapToPdfUiModel(item) }
                    .toImmutableList()
            )
        }
    }
}
