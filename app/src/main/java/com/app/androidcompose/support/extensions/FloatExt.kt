package com.app.androidcompose.support.extensions

import android.content.res.Resources
import android.util.TypedValue

fun Float.dpToPx() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    Resources.getSystem().displayMetrics,
)
