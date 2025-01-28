package leegroup.module.phototopdf.data.models

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class PdfFile(
    val name: String?,
    val uri: Uri,
    val size: Long,
    val pages: Int
)
