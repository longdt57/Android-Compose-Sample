package com.app.androidcompose.analytics.clients

import android.os.Bundle
import com.app.androidcompose.analytics.event.AnalyticsError
import com.app.androidcompose.analytics.event.AnalyticsEvent
import com.app.androidcompose.analytics.event.AnalyticsUserProperty

interface AnalyticsClient {
    @AnalyticsClientType
    val type: Int

    fun logEvent(event: AnalyticsEvent)
    fun logEvent(name: String, params: Bundle?)

    fun logHandledThrowable(error: AnalyticsError) {}
    fun log(message: String)

    fun setUserProperty(property: AnalyticsUserProperty) {}
    fun setUserId(userId: String)
}