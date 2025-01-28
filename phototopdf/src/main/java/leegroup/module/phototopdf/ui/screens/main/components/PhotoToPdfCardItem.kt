package leegroup.module.phototopdf.ui.screens.main.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import leegroup.module.compose.ui.components.DefaultCard
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.phototopdf.support.extensions.loadPdfThumbnail
import leegroup.module.phototopdf.ui.models.PdfUiModel

@Composable
internal fun PhotoToPdfCardItem(
    item: PdfUiModel,
    modifier: Modifier = Modifier,
    onclick: (PdfUiModel) -> Unit = {}
) {
    DefaultCard(
        modifier = modifier,
        content = {
            PhotoToPdfItem(item = item, onclick = onclick)
        })
}


@Composable
internal fun PhotoToPdfItem(
    item: PdfUiModel, modifier: Modifier = Modifier, onclick: (PdfUiModel) -> Unit = {}
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val size = configuration.screenWidthDp / 2

    val thumbnailBitmap = remember(item.uri) {
        context.loadPdfThumbnail(item.uri, size, size * 3 / 2)
    }
    Column(
        modifier = modifier
            .clickable {
                onclick(item)
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        thumbnailBitmap?.let {
            Image(
                painter = BitmapPainter(thumbnailBitmap.asImageBitmap()),
                contentDescription = "PDF Thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(2f / 3f)
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = item.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            text = item.sizeAndPages,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoToPdfItemPreview() {
    ComposeTheme {
        PhotoToPdfCardItem(
            item = PdfUiModel(
                name = "Sample PDF.pdf",
                sizeAndPages = "2.6 Mb, 1 page",
                Uri.parse("/Users/longdo/Downloads/Sample PDF.pdf")
            )
        )
    }
}
