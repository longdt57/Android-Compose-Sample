package com.app.androidcompose.support.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}

@Composable
fun Float.toDp(): Dp {
    val density = LocalDensity.current.density
    return Dp(this / density)
}