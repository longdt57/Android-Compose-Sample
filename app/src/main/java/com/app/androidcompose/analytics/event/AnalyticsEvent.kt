package com.app.androidcompose.analytics.event

import android.os.Bundle

class AnalyticsEvent(
    val name: String,
    val params: Bundle? = null,
)