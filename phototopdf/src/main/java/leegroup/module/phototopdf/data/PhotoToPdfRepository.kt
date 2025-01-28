package leegroup.module.phototopdf.data

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import leegroup.module.coreimage.file.ExternalFileSource
import leegroup.module.coreimage.support.extensions.loadBitmapFromUri
import leegroup.module.phototopdf.data.local.PdfSource
import leegroup.module.phototopdf.data.local.file.FileManager
import leegroup.module.phototopdf.data.models.PdfFile
import javax.inject.Inject

internal class PhotoToPdfRepository @Inject constructor(
    private val context: Context,
    private val fileManager: FileManager,
    private val pdfSource: PdfSource,
    private val externalFileSource: ExternalFileSource,
) {

    suspend fun convertUriToPdf(uri: Uri): PdfFile? {
        val bitmap = context.loadBitmapFromUri(uri) ?: return null
        return pdfSource.saveBitmapAsPdf(bitmap, fileManager.createExternalPdfFile())
    }

    suspend fun saveFileToExternalStorage(uri: Uri) {
        externalFileSource.saveFileToDocuments(uri)
    }

    suspend fun getPdfFileList(): List<PdfFile> {
        return pdfSource.getPdfFiles(fileManager.getPdfsFileDir())
    }

    fun deleteFile(uri: Uri): Boolean {
        return uri.toFile().delete()
    }
}