package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import leegroup.module.phototopdf.ui.models.PdfUiModel

@Composable
internal fun PhotoToPdfList(
    items: ImmutableList<PdfUiModel>,
    displayMode: String,
    onItemSelected: (PdfUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    PhotoToPdfGrid(
        pdfs = items,
        onItemSelected = onItemSelected,
        modifier = modifier
    )
}


