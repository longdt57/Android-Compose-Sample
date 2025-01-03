package com.app.androidcompose.tracking

import leegroup.module.analytics.AnalyticsManager
import javax.inject.Inject

class MainScreenTracker @Inject constructor(
    override val analyticsManager: AnalyticsManager
) : Launch {
    override val prefix: String get() = "MAIN_SCREEN"
}