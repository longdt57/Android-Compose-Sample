package com.app.androidcompose.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.androidcompose.data.local.preferences.base.BaseSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_SECRET_SHARED_PREFS = "app_secret_shared_prefs"

@Singleton
class EncryptedSharedPreferences @Inject constructor(applicationContext: Context) :
    BaseSharedPreferences(applicationContext, prefName = "") {

    override val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            APP_SECRET_SHARED_PREFS,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var accessToken: String? by argsNullable("access_token")
    var refreshToken: String? by argsNullable("refresh_token")
    var expiredAt: Long? by argsNullable("expired_at")

    var referrer: String? by argsNullable("referrer")
    var appInstallTime: Long? by argsNullable("appInstallTime")
}
