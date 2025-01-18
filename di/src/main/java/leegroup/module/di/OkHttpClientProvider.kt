package leegroup.module.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val NETWORK_TIMEOUT = 30L

object OkHttpClientProvider {

    fun provideOkHttpClientWithInterceptor(
        context: Context,
        isLoggingEnable: Boolean,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(provideChuckerInterceptor(context))
            addInterceptor(provideLoggingInterceptor(isLoggingEnable))

            readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            callTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    private fun provideChuckerInterceptor(
        context: Context
    ): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()
    }

    private fun provideLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when (isDebug) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
