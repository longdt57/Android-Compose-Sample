package com.app.androidcompose.support.extensions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

@Composable
@ReadOnlyComposable
fun textOrStringResource(text: String?, @StringRes id: Int?): String? {
    if (text == null) return null
    if (id == null) return null
    return stringResource(id = id)
}