package leegroup.module.extension

fun Int?.orZero() = this ?: 0

fun Int?.takeIfValidRes() = if (this != null && this > 0) this else null