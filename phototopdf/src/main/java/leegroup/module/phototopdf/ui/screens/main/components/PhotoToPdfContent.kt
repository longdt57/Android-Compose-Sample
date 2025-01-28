package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import leegroup.module.phototopdf.ui.models.PdfUiModel
import leegroup.module.phototopdf.ui.models.PhotoToPdfScreenUiModel

@Composable
internal fun PhotoToPdfContent(
    uiState: PhotoToPdfScreenUiModel,
    showRefresh: Boolean,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    onItemSelected: (PdfUiModel) -> Unit = {},
) {
    Column(modifier = modifier) {
        PhotoToPdfListTitle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            size = uiState.items.size
        )
        if (uiState.items.isNotEmpty()) {
            PhotoToPdfList(
                items = uiState.items,
                displayMode = uiState.displayMode,
                onItemSelected = onItemSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        } else if (showRefresh) {
            PhotoToPDFListEmpty(
                modifier = Modifier.fillMaxSize(),
                onRefresh = onRefresh
            )
        }
    }
}
