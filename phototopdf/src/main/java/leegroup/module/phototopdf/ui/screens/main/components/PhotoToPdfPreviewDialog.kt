package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import leegroup.module.coreimage.support.extensions.sharePdf
import leegroup.module.phototopdf.R
import leegroup.module.phototopdf.support.extensions.loadPdfThumbnail
import leegroup.module.phototopdf.ui.models.PdfUiModel

@Composable
internal fun PhotoToPdfPreviewDialog(
    item: PdfUiModel,
    onDismiss: () -> Unit,
    onSave: (PdfUiModel) -> Unit,
    onDelete: (PdfUiModel) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Make dialog fullscreen
    ) {
        val context = LocalContext.current
        val configuration = LocalConfiguration.current
        val size = configuration.screenWidthDp

        val thumbnailBitmap = remember(item) {
            context.loadPdfThumbnail(item.uri, size, size * 3 / 2)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.End
            ) {
                ShareButton {
                    context.sharePdf(item.uri)
                }
                Spacer(modifier = Modifier.width(8.dp))
                SaveButton {
                    onSave(item)
                    onDismiss()
                }
                Spacer(modifier = Modifier.width(8.dp))
                DeleteButton {
                    onDelete(item)
                    onDismiss()
                }
                Spacer(modifier = Modifier.width(8.dp))
                CloseButton(onDismiss)
            }
            thumbnailBitmap?.let {
                Image(
                    painter = BitmapPainter(thumbnailBitmap.asImageBitmap()),
                    contentDescription = "PDF Preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .aspectRatio(2 / 3f)
                )
            }
            Spacer(modifier = Modifier
                .weight(1f)
                .clickable {
                    onDismiss()
                })
        }
    }
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(R.drawable.ic_save_black_24dp),
                contentDescription = "Share",
                tint = Color.Blue,
            )
        }
    }
}

@Composable
private fun ShareButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun DeleteButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5f))
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}