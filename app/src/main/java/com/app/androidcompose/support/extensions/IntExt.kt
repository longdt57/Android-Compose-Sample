package com.app.androidcompose.support.extensions

fun Int.takeIfValidRes(): Int? = takeIf { it > 0 }

fun Int?.orZero() = this ?: 0

fun Long?.orZero() = this ?: 0L