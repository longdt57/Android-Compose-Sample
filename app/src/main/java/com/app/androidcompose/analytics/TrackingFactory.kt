package com.app.androidcompose.analytics

import com.app.androidcompose.analytics.event.AnalyticsEvent
import javax.inject.Inject

object TrackingFactory {

    interface Prefix {
        val prefix: String
        val analyticsManager: AnalyticsManager

        fun AnalyticsEvent.track() = analyticsManager.logEvent(this)
    }

    interface Launch : Prefix {
        fun launch() = AnalyticsEvent(prefix).track()
    }

    class Home @Inject constructor(override val analyticsManager: AnalyticsManager) : Launch {
        override val prefix: String get() = "HOME"
    }
}
