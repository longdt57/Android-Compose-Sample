package com.app.androidcompose.tracking

import leegroup.module.analytics.AnalyticsManager
import leegroup.module.analytics.event.AnalyticsEvent

interface Tracking {
    val prefix: String
    val analyticsManager: AnalyticsManager

    fun track(event: AnalyticsEvent) = analyticsManager.logEvent(event)
}

interface Launch : Tracking {
    fun launch() = track(
        AnalyticsEvent("${prefix}_LAUNCH")
    )
}
