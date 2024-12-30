package com.app.androidcompose.di.data

import com.app.androidcompose.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import leegroup.module.data.remote.services.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val network = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        return network.asConverterFactory(
            "application/json".toMediaType()
        )
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppRetrofit

    @Singleton
    @Provides
    @AppRetrofit
    fun provideAppRetrofit(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    fun provideService(@AppRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
