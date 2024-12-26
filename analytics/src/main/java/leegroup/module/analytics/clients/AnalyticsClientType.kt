@file:JvmName("AnalyticsClientType")

package leegroup.module.analytics.clients

import androidx.annotation.IntDef
import leegroup.module.analytics.clients.ClientType.ALL
import leegroup.module.analytics.clients.ClientType.APPSFLYER
import leegroup.module.analytics.clients.ClientType.FACEBOOK
import leegroup.module.analytics.clients.ClientType.FIREBASE
import leegroup.module.analytics.clients.ClientType.INSIDER

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
