package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import leegroup.module.compose.support.extensions.getScreenSize
import leegroup.module.compose.ui.components.LoadMoreGrid
import leegroup.module.compose.ui.models.ScreenSize
import leegroup.module.phototopdf.ui.models.PdfUiModel

@Composable
internal fun PhotoToPdfGrid(
    pdfs: ImmutableList<PdfUiModel>,
    modifier: Modifier = Modifier,
    onItemSelected: (PdfUiModel) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val columnCount = when (getScreenSize()) {
        ScreenSize.SMALL -> 2
        ScreenSize.MEDIUM -> 3
        ScreenSize.LARGE -> 4
    }

    val listState = rememberLazyGridState()
    LoadMoreGrid(gridState = listState, onLoadMore = onLoadMore)
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columnCount),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = { GridItemSpan(columnCount) }) {
            Spacer(modifier = Modifier.padding(top = 8.dp))
        }

        items(pdfs, key = { it.uri.path.orEmpty() }) {
            PhotoToPdfCardItem(item = it, onclick = onItemSelected)
        }

        item(span = { GridItemSpan(columnCount) }) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


