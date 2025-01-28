package leegroup.module.phototopdf.ui.models

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class PdfUiModel(
    val name: String,
    val sizeAndPages: String, // Size in Kb or Mb
    val uri: Uri,
)
