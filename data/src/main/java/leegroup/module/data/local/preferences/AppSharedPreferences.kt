package leegroup.module.data.local.preferences

import android.content.Context
import javax.inject.Inject

private const val APP_SHARED_PREFERENCES_NAME = "app_preferences"

class AppSharedPreferences @Inject constructor(
    context: Context
) : BaseSharedPreferences(context, APP_SHARED_PREFERENCES_NAME) {

    var isFirstTimeOpenApp: Boolean by args(defaultValue = true)
}