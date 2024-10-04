package com.app.androidcompose.support.util

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

@SuppressWarnings("SwallowedException")
object JsonUtil {

    inline fun <reified T> decodeFromString(json: String): T? {
        return try {
            Json.decodeFromString<T>(json)
        } catch (ex: SerializationException) {
            null
        } catch (ex: IllegalArgumentException) {
            null
        }
    }

    inline fun <reified T> encodeToString(value: T): String {
        return Json.encodeToString(value)
    }

    fun getJsonConverter(): Converter.Factory {

        val network = Json { ignoreUnknownKeys = true }
        return network.asConverterFactory(
            "application/json".toMediaType()
        )
    }
}
