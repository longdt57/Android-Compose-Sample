package leegroup.module.phototopdf.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import leegroup.module.phototopdf.data.models.PdfFile
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

internal class PdfSource @Inject constructor(
    private val context: Context,
) {

    suspend fun saveBitmapAsPdf(
        bitmap: Bitmap,
        file: File,
    ): PdfFile {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        pdfDocument.finishPage(page)

        val fileOutputStream = withContext(Dispatchers.IO) {
            FileOutputStream(file)
        }

        pdfDocument.writeTo(fileOutputStream)

        // Close resources
        pdfDocument.close()
        withContext(Dispatchers.IO) {
            fileOutputStream.flush()
            fileOutputStream.close()
        }

        val uri = file.toUri()

        return PdfFile(
            name = file.name,
            uri = file.toUri(),
            size = file.length(),
            pages = getPdfPageCount(uri)
        )
    }

    suspend fun getPdfFiles(directory: File): List<PdfFile> =
        coroutineScope {
            // Check all files in the directory
            val files = directory.listFiles()
                ?.filter { it.isFile && it.extension.equals("pdf", ignoreCase = true) }

            return@coroutineScope files?.map { file ->
                async {
                    try {
                        val uri = Uri.fromFile(file)
                        val numPages = getPdfPageCount(uri)

                        PdfFile(
                            name = file.name,
                            uri = uri,
                            size = file.length(),
                            pages = numPages
                        )
                    } catch (e: Exception) {
                        Timber.e("PdfFile", "Error reading PDF: ${file.name}")
                        null
                    }
                }
            }
                ?.awaitAll()
                ?.filterNotNull()
                .orEmpty()
        }

    private fun getPdfPageCount(uri: Uri): Int {
        return try {
            val parcelFileDescriptor: ParcelFileDescriptor? =
                context.contentResolver.openFileDescriptor(uri, "r")
            val pdfRenderer = PdfRenderer(parcelFileDescriptor!!)
            val pageCount = pdfRenderer.pageCount
            pdfRenderer.close()
            parcelFileDescriptor.close()
            pageCount
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}