package leegroup.module.phototopdf.ui.screens.main.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.phototopdf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoToPdfAppBar(
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.photo_to_pdf_screen_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ScreenPreview() {
    ComposeTheme {
        PhotoToPdfAppBar()
    }
}