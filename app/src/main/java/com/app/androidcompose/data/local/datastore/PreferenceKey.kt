package com.app.androidcompose.data.local.datastore

import androidx.datastore.preferences.core.stringSetPreferencesKey

object PreferenceKey {

    val userIdsKey = stringSetPreferencesKey("userIds")
}