package leegroup.module.compose.support.extensions

import androidx.compose.ui.graphics.Color

fun String.hexToColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}