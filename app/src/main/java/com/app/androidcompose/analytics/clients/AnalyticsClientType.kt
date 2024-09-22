@file:JvmName("AnalyticsClientType")

package com.app.androidcompose.analytics.clients

import androidx.annotation.IntDef
import com.app.androidcompose.analytics.clients.ClientType.ALL
import com.app.androidcompose.analytics.clients.ClientType.APPSFLYER
import com.app.androidcompose.analytics.clients.ClientType.FACEBOOK
import com.app.androidcompose.analytics.clients.ClientType.FIREBASE
import com.app.androidcompose.analytics.clients.ClientType.INSIDER

@IntDef(FIREBASE, FACEBOOK, INSIDER, APPSFLYER, ALL)
@Retention(AnnotationRetention.SOURCE)
annotation class AnalyticsClientType

object ClientType {
    const val FIREBASE = 0b000001
    const val FACEBOOK = 0b000010
    const val INSIDER = 0b000100
    const val APPSFLYER = 0b001000
    const val ALL = FIREBASE or FACEBOOK or INSIDER or APPSFLYER
}
