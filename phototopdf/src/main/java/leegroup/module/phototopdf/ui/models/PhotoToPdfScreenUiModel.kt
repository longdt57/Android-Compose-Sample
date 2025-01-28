package leegroup.module.phototopdf.ui.models

import androidx.annotation.StringDef
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import leegroup.module.phototopdf.ui.models.PhotoToPdfDisplayMode.Companion.GRID
import leegroup.module.phototopdf.ui.models.PhotoToPdfDisplayMode.Companion.LIST

@Immutable
internal data class PhotoToPdfScreenUiModel(
    val items: ImmutableList<PdfUiModel> = persistentListOf(),
    @PhotoToPdfDisplayMode val displayMode: String = GRID,
) {
    fun removeItem(item: PdfUiModel): PhotoToPdfScreenUiModel =
        copy(items = (items - item).toImmutableList())

    fun addToStart(item: PdfUiModel): PhotoToPdfScreenUiModel = copy(
        items = (listOf(item) + items).toImmutableList()
    )
}


@Retention(AnnotationRetention.SOURCE)
@StringDef(GRID, LIST)
internal annotation class PhotoToPdfDisplayMode {
    companion object {
        const val GRID = "GRID"
        const val LIST = "LIST"
    }
}