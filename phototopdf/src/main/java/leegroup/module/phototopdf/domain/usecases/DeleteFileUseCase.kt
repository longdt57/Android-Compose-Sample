package leegroup.module.phototopdf.domain.usecases

import android.net.Uri
import kotlinx.coroutines.flow.flow
import leegroup.module.phototopdf.data.PhotoToPdfRepository
import javax.inject.Inject

internal class DeleteFileUseCase @Inject constructor(
    private val repository: PhotoToPdfRepository,
) {

    operator fun invoke(uri: Uri) = flow {
        emit(repository.deleteFile(uri))
    }
}