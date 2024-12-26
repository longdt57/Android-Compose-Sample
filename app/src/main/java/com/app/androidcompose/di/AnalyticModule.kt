package com.app.androidcompose.di

import leegroup.module.analytics.AnalyticsManager
import leegroup.module.analytics.clients.ClientType
import leegroup.module.analytics.clients.FirebaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AnalyticModule {

    @Provides
    fun provideAnalytics(
        firebaseClient: leegroup.module.analytics.clients.FirebaseClient
    ): leegroup.module.analytics.AnalyticsManager {
        return leegroup.module.analytics.AnalyticsManager(
            listOf(firebaseClient),
            defaultLogEventClientType = leegroup.module.analytics.clients.ClientType.FIREBASE
        )
    }

}