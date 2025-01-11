package leegroup.module.compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import leegroup.module.compose.R
import leegroup.module.compose.ui.models.LoadingState
import leegroup.module.compose.ui.theme.ComposeTheme
import leegroup.module.extension.takeIfValidRes

@Composable
fun LoadingProgress(loading: LoadingState.Loading) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            loading.messageRes.takeIfValidRes()?.let {
                val text = stringResource(id = it)
                Text(modifier = Modifier.padding(8.dp), text = text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingProgressPreview() {
    ComposeTheme {
        LoadingProgress(loading = LoadingState.Loading(messageRes = R.string.loading))
    }
}