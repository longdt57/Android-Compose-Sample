package com.app.androidcompose.data.repository

import com.app.androidcompose.BuildConfig.FLAVOR
import com.app.androidcompose.R
import com.app.androidcompose.support.application.AppConst.FLAVOR_STAGING
import com.app.androidcompose.data.model.remoteconfig.AppRemoteConfig
import com.app.androidcompose.support.util.JsonUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig

object RemoteConfigRepository {

    fun fetch() {
        Firebase.remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        val minimumFetchIntervalInSeconds = if (FLAVOR == FLAVOR_STAGING) 0L else 0L
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(minimumFetchIntervalInSeconds)
            .build()
        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
        Firebase.remoteConfig.fetchAndActivate()
            .addOnCompleteListener {}
    }

    val appConfig: AppRemoteConfig = getObject("APP_CONFIG") ?: AppRemoteConfig()

    private inline fun <reified T> getObject(key: String): T? {
        return JsonUtil.decodeFromString(
            Firebase.remoteConfig.getString(key)
        )
    }
}