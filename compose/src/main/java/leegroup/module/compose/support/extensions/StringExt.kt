package leegroup.module.compose.support.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

fun String.hexToColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

fun randomString(length: Int): String = LoremIpsum(length).values.first()