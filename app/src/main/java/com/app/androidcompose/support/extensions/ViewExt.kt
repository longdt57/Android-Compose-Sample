package com.app.androidcompose.support.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle

fun View.setOnSingleClickListener(action: (View) -> Unit) {
    setOnClickListener { view ->
        view.isClickable = false
        action(view)
        view.postDelayed(
            {
                view.isClickable = true
            },
            300L
        )
    }
}

fun FragmentActivity.isInForeGround(): Boolean {
    return lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
}

fun Fragment.isInForeGround(): Boolean {
    return lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
}
