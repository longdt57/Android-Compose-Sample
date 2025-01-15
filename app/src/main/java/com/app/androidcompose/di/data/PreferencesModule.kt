package com.app.androidcompose.di.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.data.local.datastore.AppDataStore


@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    fun provideAppPreferencesDataStore(
        @ApplicationContext appContext: Context
    ): AppDataStore {
        return AppDataStore(appContext)
    }
}
