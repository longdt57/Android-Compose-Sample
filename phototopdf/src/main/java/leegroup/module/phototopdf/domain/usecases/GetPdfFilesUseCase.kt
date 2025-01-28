package leegroup.module.phototopdf.domain.usecases

import kotlinx.coroutines.flow.flow
import leegroup.module.phototopdf.data.PhotoToPdfRepository
import javax.inject.Inject

internal class GetPdfFilesUseCase @Inject constructor(
    private val repository: PhotoToPdfRepository,
) {

    operator fun invoke() = flow {
        emit(repository.getPdfFileList())
    }
}