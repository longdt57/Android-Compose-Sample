package leegroup.module.photosample.domain.models

data class PhotoModelD(
    val id: Int,
    val albumId: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String,
)
