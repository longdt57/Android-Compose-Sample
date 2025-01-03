package com.app.androidcompose.di

import android.content.Context
import com.app.androidcompose.support.util.DispatchersProvider
import com.app.androidcompose.support.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideDispatchersProvider(dispatcher: DispatchersProviderImpl): DispatchersProvider =
        dispatcher
}
