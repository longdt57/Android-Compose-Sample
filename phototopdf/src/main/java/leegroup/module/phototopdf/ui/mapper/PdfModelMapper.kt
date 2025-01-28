package leegroup.module.phototopdf.ui.mapper

import android.content.Context
import leegroup.module.phototopdf.data.models.PdfFile
import leegroup.module.phototopdf.support.utils.PdfModelUtil
import leegroup.module.phototopdf.ui.models.PdfUiModel
import javax.inject.Inject

internal class PdfModelMapper @Inject constructor(
    private val context: Context
) {

    fun mapToPdfUiModel(pdfFile: PdfFile): PdfUiModel {
        return PdfUiModel(
            name = pdfFile.name ?: pdfFile.uri.lastPathSegment.orEmpty(),
            sizeAndPages = PdfModelUtil.formatSizeAndPages(pdfFile.size, pdfFile.pages, context),
            uri = pdfFile.uri
        )
    }
}