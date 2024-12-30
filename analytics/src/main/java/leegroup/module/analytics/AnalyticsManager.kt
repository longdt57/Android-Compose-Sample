package leegroup.module.analytics

import leegroup.module.analytics.clients.AnalyticsClient
import leegroup.module.analytics.clients.AnalyticsClientType
import leegroup.module.analytics.clients.ClientType
import leegroup.module.analytics.event.AnalyticsError
import leegroup.module.analytics.event.AnalyticsEvent
import leegroup.module.analytics.event.AnalyticsUserProperty

/**
 * Clients
 * Can be single client: clients = FIREBASE, clients = APPSFLYER
 * or multiple: clients = FIREBASE or APPSFLYER
 * or all: clients = ALL
 */
class AnalyticsManager(
    private val list: List<AnalyticsClient>,
    @AnalyticsClientType private val defaultLogEventClientType: Int = ClientType.FIREBASE
) {

    fun logEvent(
        event: AnalyticsEvent,
        @AnalyticsClientType clients: Int = defaultLogEventClientType
    ) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.logEvent(event)
            }
        }
    }

    fun setUserProperty(
        property: AnalyticsUserProperty,
        @AnalyticsClientType clients: Int = defaultLogEventClientType
    ) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.setUserProperty(property)
            }
        }
    }

    fun logHandledThrowable(
        error: AnalyticsError,
        @AnalyticsClientType clients: Int = defaultLogEventClientType
    ) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.logHandledThrowable(error)
            }
        }
    }

    fun log(
        message: String,
        @AnalyticsClientType clients: Int = defaultLogEventClientType
    ) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.log(message)
            }
        }
    }

    fun setUserId(
        userId: String,
        @AnalyticsClientType clients: Int = defaultLogEventClientType
    ) {
        list.forEach {
            invokeIfValid(clients, it) {
                it.setUserId(userId)
            }
        }
    }

    private fun invokeIfValid(
        clients: Int,
        client: AnalyticsClient, callback: () -> Unit
    ) {
        val isValid = (clients and client.type) != 0
        if (isValid) {
            callback.invoke()
        }
    }

}
