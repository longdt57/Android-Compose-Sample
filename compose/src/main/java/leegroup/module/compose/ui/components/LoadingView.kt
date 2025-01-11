package leegroup.module.compose.ui.components

import androidx.compose.runtime.Composable
import leegroup.module.compose.ui.models.LoadingState

@Composable
fun LoadingView(loading: LoadingState) {
    when (loading) {
        is LoadingState.Loading -> LoadingProgress(loading)
        else -> {}
    }
}
