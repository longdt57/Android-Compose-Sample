package com.app.androidcompose.support.extensions

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.ceil

fun Float.dpToPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics,
)

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Float.ceilToInt(): Int {
    return ceil(this).toInt()
}
