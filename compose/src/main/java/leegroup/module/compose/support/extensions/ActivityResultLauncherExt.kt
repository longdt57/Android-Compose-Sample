package leegroup.module.compose.support.extensions

import androidx.activity.result.ActivityResultLauncher

fun ActivityResultLauncher<String>.launchImage() {
    launch("image/*")
}