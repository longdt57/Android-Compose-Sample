package leegroup.module.analytics.clients

import android.os.Bundle
import leegroup.module.analytics.event.AnalyticsError
import leegroup.module.analytics.event.AnalyticsEvent
import leegroup.module.analytics.event.AnalyticsUserProperty

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