package com.app.androidcompose.support.extensions

import kotlin.math.ceil

fun Float.ceilToInt(): Int {
    return ceil(this).toInt()
}
