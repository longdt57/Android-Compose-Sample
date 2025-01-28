package leegroup.module.phototopdf.support.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor

fun Context.loadPdfThumbnail(uri: Uri, width: Int, height: Int): Bitmap? {
    return try {
        // Open a file descriptor for the given URI
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(uri, "r")
        parcelFileDescriptor?.use { pfd ->
            // Create a PdfRenderer with the file descriptor
            val pdfRenderer = PdfRenderer(pfd)
            pdfRenderer.use {
                if (it.pageCount > 0) {
                    // Open the first page of the PDF
                    val page = it.openPage(0)
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    // Render the page into the bitmap
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    bitmap
                } else {
                    null // Return null if the PDF has no pages
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null // Return null in case of errors
    }
}
