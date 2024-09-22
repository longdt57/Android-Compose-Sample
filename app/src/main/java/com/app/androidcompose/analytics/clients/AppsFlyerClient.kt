//package com.app.androidcompose.analytics.clients
//
//import android.content.Context
//import android.os.Bundle
//import com.appsflyer.AppsFlyerLib
//import com.app.androidcompose.analytics.clients.AnalyticsClient
//import com.app.androidcompose.analytics.event.AnalyticsError
//import com.app.androidcompose.analytics.event.AnalyticsEvent
//import com.app.androidcompose.analytics.event.AnalyticsScreenView
//import com.app.androidcompose.analytics.event.AnalyticsUserProperty
//import com.app.androidcompose.analytics.ClientType
//
//class AppsFlyerClient(
//    private val context: Context,
//) : AnalyticsClient {
//
//    override val type: Int get() = ClientType.APPSFLYER
//
//    private val appsFlyerLib: AppsFlyerLib get() = AppsFlyerLib.getInstance()
//
//    override fun logEvent(event: AnalyticsEvent) {
//        appsFlyerLib.logEvent(context.applicationContext, event.name, event.params)
//    }
//
//    override fun logEvent(name: String, params: Bundle?) {
//        appsFlyerLib.logEvent(context.applicationContext, name, params?.toMap())
//    }
//
//    override fun setUserProperty(property: AnalyticsUserProperty) {
//        // nothing
//    }
//
//    override fun logHandledThrowable(error: AnalyticsError) {
//        appsFlyerLib.logEvent(
//            context.applicationContext, error.name, mapOf(
//                "error" to (error.throwable.localizedMessage.orEmpty())
//            )
//        )
//    }
//
//    override fun log(message: String) {
//        // do nothing
//    }
//
//    override fun setUserId(userId: String) {
//        appsFlyerLib.setCustomerUserId(userId)
//    }
//
//    private fun Bundle.toMap(): Map<String, Any?> {
//        val bundle = this
//        val map = mutableMapOf<String, Any?>()
//        for (key in bundle.keySet()) {
//            map[key] = bundle.get(key)
//        }
//        return map
//    }
//}