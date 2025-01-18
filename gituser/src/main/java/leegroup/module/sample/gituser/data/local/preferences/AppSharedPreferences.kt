package leegroup.module.sample.gituser.data.local.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import leegroup.module.extension.sharepreference.BaseSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_SHARED_PREFERENCES_NAME = "app_preferences"

@Singleton
class AppSharedPreferences @Inject constructor(
    @ApplicationContext context: Context,
) : BaseSharedPreferences(context, APP_SHARED_PREFERENCES_NAME) {

    var isFirstTimeOpenApp: Boolean by args(defaultValue = true)
}