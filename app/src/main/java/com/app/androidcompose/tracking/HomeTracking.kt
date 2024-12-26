package com.app.androidcompose.tracking

import javax.inject.Inject
import leegroup.module.analytics.AnalyticsManager

class HomeTracking @Inject constructor(
    override val analyticsManager: AnalyticsManager
) : Launch {
    override val prefix: String get() = "HOME"
}