package leegroup.module.phototopdf.data.local.file

import android.content.Context
import leegroup.module.phototopdf.support.utils.DateTimeUtils
import java.io.File
import javax.inject.Inject

internal class FileManager @Inject constructor(
    private val context: Context
) {

    fun createExternalPdfFile(fileName: String = DateTimeUtils.getCurrentDateTimeString()): File {
        val pdfDir = getPdfsFileDir()
        return File(pdfDir, "$fileName$PDF_EXTENSION")
    }

    fun getPdfsFileDir(): File {
        val pdfDir = File(context.getExternalFilesDir(null), PDFS)
        if (pdfDir.exists().not()) {
            pdfDir.mkdirs()
        }
        return pdfDir
    }


    companion object {
        private const val PDFS = "PDFs"
        private const val PDF_EXTENSION = ".pdf"
    }
}
