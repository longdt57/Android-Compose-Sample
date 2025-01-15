package leegroup.module.compose.support.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun String.hexToColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

fun randomString(words: Int): String = LoremIpsum(words).values.joinToString()