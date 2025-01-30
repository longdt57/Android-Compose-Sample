package leegroup.module.compose.support.extensions

import java.text.NumberFormat
import java.util.Locale

fun Long.formatNumber(): String =
    NumberFormat.getInstance(Locale.getDefault()).format(this)