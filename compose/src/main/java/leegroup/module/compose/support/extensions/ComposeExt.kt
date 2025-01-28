package leegroup.module.compose.support.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import leegroup.module.compose.ui.models.ScreenSize

@Composable
fun getScreenSize(): ScreenSize {
    val configuration = LocalConfiguration.current
    return ScreenSize(configuration.screenWidthDp)
}
