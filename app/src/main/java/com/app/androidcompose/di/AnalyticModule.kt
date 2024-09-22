package com.app.androidcompose.di

import com.app.androidcompose.analytics.AnalyticsManager
import com.app.androidcompose.analytics.clients.ClientType
import com.app.androidcompose.analytics.clients.FirebaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticModule {

    @Provides
    fun provideAnalytics(
        firebaseClient: FirebaseClient
    ): AnalyticsManager {
        return AnalyticsManager(
            listOf(firebaseClient),
            defaultLogEventClientType = ClientType.FIREBASE
        )
    }

}