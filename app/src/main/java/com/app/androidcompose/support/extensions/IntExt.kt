package com.app.androidcompose.support.extensions

fun Int.takeIfValidRes(): Int? = takeIf { it > 0 }
