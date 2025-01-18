package leegroup.module.photosample.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import leegroup.module.photosample.domain.models.PhotoModelD

@Serializable
data class PhotoModel(
    @SerialName("id")
    val id: Int,

    @SerialName("albumId")
    val albumId: Int,

    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,

    @SerialName("title")
    val title: String,

    @SerialName("url")
    val url: String
)

fun PhotoModel.mapToDomain() = PhotoModelD(
    id = id,
    albumId = albumId,
    thumbnailUrl = thumbnailUrl,
    title = title,
    url = url,
)

fun List<PhotoModel>.mapToDomain() = map { it.mapToDomain() }
