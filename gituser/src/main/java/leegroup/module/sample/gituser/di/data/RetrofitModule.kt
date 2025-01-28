package leegroup.module.sample.gituser.di.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.compose.di.RetrofitProvider
import leegroup.module.sample.gituser.BuildConfig
import leegroup.module.sample.gituser.data.remote.services.GitUserApiService
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal class RetrofitModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class GitUserRetrofit

    @GitUserRetrofit
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
    fun provideService(@GitUserRetrofit retrofit: Retrofit): GitUserApiService {
        return retrofit.create(GitUserApiService::class.java)
    }
}
