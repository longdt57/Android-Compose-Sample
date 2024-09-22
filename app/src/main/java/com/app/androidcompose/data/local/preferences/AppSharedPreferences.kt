package com.app.androidcompose.data.local.preferences

import android.content.Context
import com.app.androidcompose.data.local.preferences.base.BaseSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(
    @ApplicationContext context: Context
) : BaseSharedPreferences(context, SharedPreferenceConst.APP_SHARED_PREFERENCES_NAME) {

    var isFirstTimeOpenApp: Boolean by args(defaultValue = true)
}