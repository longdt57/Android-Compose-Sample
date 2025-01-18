package leegroup.module.photosample.domain.params

data class GetPhotoListParam(
    val ids: List<Int>? = null,
    val titleLike: String? = null,
    val since: Int,
    val limit: Int
) {
    fun isQueryEmpty() = titleLike.isNullOrEmpty()
}