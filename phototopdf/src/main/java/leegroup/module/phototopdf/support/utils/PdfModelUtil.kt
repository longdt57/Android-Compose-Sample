package leegroup.module.phototopdf.support.utils

import android.content.Context
import leegroup.module.phototopdf.R
import java.util.Locale

object PdfModelUtil {

    fun formatSizeAndPages(sizeInBytes: Long, pages: Int, context: Context): String {
        val sizeInKb = sizeInBytes / 1024.0
        val sizeFormatted = if (sizeInKb >= 1024) {
            String.format(Locale.getDefault(), "%.2f MB", sizeInKb / 1024)
        } else {
            String.format(Locale.getDefault(), "%.2f KB", sizeInKb)
        }

        return if (pages > 1) {
            "$sizeFormatted • $pages " + context.getString(R.string.pages)
        } else if (pages == 1) {
            "$sizeFormatted • $pages " + context.getString(R.string.page)
        } else {
            sizeFormatted
        }
    }
}