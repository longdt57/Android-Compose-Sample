package leegroup.module.photosample.ui.screens.main.photolist

import leegroup.module.photosample.ui.models.PhotoUiModel

internal sealed interface PhotoListAction {
    data object LoadIfEmpty : PhotoListAction
    data object LoadMore : PhotoListAction
    data object FavoriteFilterClick : PhotoListAction
    data class Query(
        val query: String,
        val delay: Long = if (query.isNotEmpty()) QUERY_DELAY else 0
    ) : PhotoListAction

    class FavoriteItemClick(val item: PhotoUiModel) : PhotoListAction
    class PhotoClick(val item: PhotoUiModel) : PhotoListAction

    companion object {
        const val QUERY_DELAY = 500L

    }
}