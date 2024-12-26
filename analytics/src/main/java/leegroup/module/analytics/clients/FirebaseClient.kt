package leegroup.module.analytics.clients

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import leegroup.module.analytics.event.AnalyticsError
import leegroup.module.analytics.event.AnalyticsEvent
import leegroup.module.analytics.event.AnalyticsUserProperty
import timber.log.Timber

class FirebaseClient @Inject constructor() : AnalyticsClient {

    override val type: Int get() = ClientType.FIREBASE

    private val firebaseAnalytics: FirebaseAnalytics get() = Firebase.analytics

    override fun logEvent(event: AnalyticsEvent) {
        val name = event.name.replace('.', '_')
        val bundle = event.params
        firebaseAnalytics.logEvent(name, bundle)
        Timber.d("event $name $bundle")
    }

    override fun logEvent(name: String, params: Bundle?) {
        val refactorName = name.replace('.', '_')
        firebaseAnalytics.logEvent(refactorName, params)
        Timber.d("event $refactorName ${params.toString()}")
    }

    override fun setUserProperty(property: AnalyticsUserProperty) {
        val value: String? = property.value.takeUnless { it.isNullOrBlank() }
        firebaseAnalytics.setUserProperty(property.name, value)
    }

    override fun logHandledThrowable(error: AnalyticsError) {
        Timber.w(error.throwable, error.name)
    }

    override fun log(message: String) {
        Timber.d(message)
    }

    override fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)

        firebaseAnalytics.setUserProperty(FIREBASE_USER_ID_PROPERTY, userId)
    }

    companion object {
        private const val FIREBASE_USER_ID_PROPERTY = "user_id"
    }
}