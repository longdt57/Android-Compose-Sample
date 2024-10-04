package com.app.androidcompose.support.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.TypedValue
import android.widget.Toast
import timber.log.Timber

private const val TAG = "ContextExt"

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Context.openSettings() {
    try {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    } catch (e: ActivityNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
    }
}

fun Context.openAppSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}

fun Context.shareText(content: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    try {
        startActivity(sendIntent)
    } catch (e: ActivityNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
    }
}

fun Context.callTo(phone: String) {
    val callIntent = Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:$phone")
    }
    try {
        startActivity(callIntent)
    } catch (e: ActivityNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
    }
}

fun Context.sendEmail(email: String) {
    val emailIntent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:$email")
    }
    try {
        startActivity(emailIntent)
    } catch (e: ActivityNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
    }
}

fun Context.openPlayStore(pkg: String = packageName) {
    try {
        startActivity(
            Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("market://details?id=$pkg")
            }
        )
    } catch (e: ActivityNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
    }
}

fun Context.gotoGMap(address: String) {
    val gMapAppPackageName = "com.google.android.apps.maps"
    val mapIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$address")
    )

    if (isAppEnabled(gMapAppPackageName, packageManager)) {
        mapIntent.`package` = gMapAppPackageName
        startActivity(mapIntent)
    } else {
        startActivity(mapIntent)
    }
}

fun Context.getStringByIdName(idName: String?): String? {
    return try {
        resources.getString(resources.getIdentifier(idName, "string", packageName))
    } catch (ex: Exception) {
        null
    }
}

private fun Context.isAppEnabled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        appInfo.enabled
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.tag(TAG).d(e.message.orEmpty())
        false
    }
}
