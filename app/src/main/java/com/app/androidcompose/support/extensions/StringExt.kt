package com.app.androidcompose.support.extensions

fun String.formattedUrl(): String {
    return if (this.startsWith("http://") || this.startsWith("https://")) {
        this
    } else {
        "http://$this"
    }
}