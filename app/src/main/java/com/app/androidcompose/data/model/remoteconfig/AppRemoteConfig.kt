package com.app.androidcompose.data.model.remoteconfig

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class AppRemoteConfig(
    @SerialName("min_force_update_app_version")
    val minForceUpdateAppVersion: String? = null,
    @SerialName("min_suggested_app_version")
    val minSuggestedAppVersion: String? = null,
)