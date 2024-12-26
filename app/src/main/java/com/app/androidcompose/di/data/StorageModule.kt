package com.app.androidcompose.di.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.data.local.preferences.AppSharedPreferences
import leegroup.module.data.local.preferences.EncryptedSharedPreferences

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    companion object {

        @Provides
        fun provideEncryptedSharedPreferences(@ApplicationContext context: Context) =
            EncryptedSharedPreferences(context)

        @Provides
        fun provideSharedPreferencesStorage(@ApplicationContext context: Context) =
            AppSharedPreferences(context)
    }
}
