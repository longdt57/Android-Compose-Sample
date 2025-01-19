package leegroup.module.photosample.ui.screens.main.photolist

import kotlinx.coroutines.delay
import leegroup.module.extension.orZero
import leegroup.module.photosample.domain.params.GetPhotoListParam
import leegroup.module.photosample.ui.models.PhotoListUiModel

const val PER_PAGE = 30
const val QUERY_DELAY = 500L

internal fun PhotoListUiModel.createLoadMoreQueryParam() = GetPhotoListParam(
    ids = if (isFavoriteEnabled) favoriteList.toList() else null,
    titleLike = query,
    since = photos.lastOrNull()?.id.orZero(),
    limit = PER_PAGE,
)

internal fun PhotoListUiModel.canLoadMore(): Boolean {
    return hasMore && (isFavoriteEnabled && photos.size == favoriteList.size).not()
}

internal suspend fun PhotoListUiModel.delayQuery() {
    if (query.isNotEmpty()) {
        delay(QUERY_DELAY)
    }
}

internal fun PhotoListViewModel.isEmpty() = uiModel.value.photos.isEmpty()
