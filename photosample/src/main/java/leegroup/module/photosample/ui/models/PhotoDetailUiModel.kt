package leegroup.module.photosample.ui.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class PhotoDetailUiModel(
    val id: Int = 0,
    val title: String = "",
    val url: String = "",
    val isFavorite: Boolean = false,
)
