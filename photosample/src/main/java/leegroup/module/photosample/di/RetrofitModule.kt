package leegroup.module.photosample.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.di.RetrofitProvider
import leegroup.module.photosample.BuildConfig
import leegroup.module.photosample.data.remote.services.PhotoApiService
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal class RetrofitModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class PhotoRetrofit

    @PhotoRetrofit
    @Provides
    fun provideAppRetrofit(
        @ApplicationContext context: Context,
    ): Retrofit {
        return RetrofitProvider.provideAppRetrofit(
            context = context,
            isLoggingEnable = BuildConfig.DEBUG,
            baseUrl = BuildConfig.BASE_API_URL
        )
    }

    @Provides
    fun provideService(@PhotoRetrofit retrofit: Retrofit): PhotoApiService {
        return retrofit.create(PhotoApiService::class.java)
    }
}
