package com.app.androidcompose.ui

import android.content.Context
import com.app.androidcompose.R
import leegroup.module.domain.exceptions.ApiException
import com.app.androidcompose.support.extensions.showToast

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is ApiException -> error?.message
        else -> message
    } ?: context.getString(R.string.error_generic)
}

fun Throwable.showToast(context: Context) =
    context.showToast(userReadableMessage(context))
