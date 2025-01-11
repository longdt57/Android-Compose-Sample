package leegroup.module.extension

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object JsonUtil {

    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    inline fun <reified T> decodeFromString(value: String): T? {
        return try {
            json.decodeFromString<T>(value)
        } catch (ex: SerializationException) {
            null
        } catch (ex: IllegalArgumentException) {
            null
        }
    }

    inline fun <reified T> encodeToString(value: T): String {
        return json.encodeToString(value)
    }

    inline fun <reified T> encodeToMap(value: T): Map<String, Any> {
        val jsonObject = json.encodeToJsonElement(value).jsonObject
        return jsonObject.mapValues { it.value.jsonPrimitive.content }
    }
}
