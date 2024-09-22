package com.app.androidcompose.di.data.network

import com.app.androidcompose.BuildConfig
import com.app.androidcompose.support.util.JsonUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppRetrofit

    @Singleton
    @Provides
    @AppRetrofit
    fun provideAppRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(JsonUtil.getJsonConverter())
            .build()
    }
}
