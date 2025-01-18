package leegroup.module.photosample.ui.screens.main.photolist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import leegroup.module.compose.R
import leegroup.module.compose.ui.theme.ComposeTheme

@Composable
internal fun PhotoListEmpty(modifier: Modifier = Modifier, onRefresh: () -> Unit) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Button(onClick = onRefresh) {
            Text(
                text = stringResource(id = R.string.common_retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GitUserListEmptyPreview() {
    ComposeTheme {
        PhotoListEmpty(modifier = Modifier.fillMaxSize()) {}
    }
}
