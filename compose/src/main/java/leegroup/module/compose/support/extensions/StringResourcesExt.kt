package leegroup.module.compose.support.extensions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

@Composable
@ReadOnlyComposable
fun stringResourceOrNull(@StringRes id: Int?): String? {
    if (id == null || id == 0 || id == -1) return null
    return stringResource(id)
}