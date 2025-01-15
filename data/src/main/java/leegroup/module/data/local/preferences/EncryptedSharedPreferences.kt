package leegroup.module.data.local.preferences

import android.content.Context
import leegroup.module.extension.sharepreference.BaseEncryptedSharedPreferences
import javax.inject.Inject

private const val APP_SECRET_SHARED_PREFS = "app_secret_shared_prefs"

class EncryptedSharedPreferences @Inject constructor(applicationContext: Context) :
    BaseEncryptedSharedPreferences(applicationContext, prefName = APP_SECRET_SHARED_PREFS) {

    var accessToken: String? by argsNullable("access_token")
}
