package leegroup.module.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit

object RetrofitProvider {

    private fun provideConverterFactory(): Converter.Factory {
        val network = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        return network.asConverterFactory(
            "application/json".toMediaType()
        )
    }

    fun provideAppRetrofit(
        context: Context,
        isLoggingEnable: Boolean,
        baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClientProvider.provideOkHttpClientWithInterceptor(
                    context, isLoggingEnable
                )
            )
            .addConverterFactory(provideConverterFactory())
            .build()
    }

}