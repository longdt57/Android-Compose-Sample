package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import leegroup.module.phototopdf.R

@Composable
internal fun PhotoToPdfListTitle(size: Int, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.photo_to_pdf_list_title, size),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
    )
}