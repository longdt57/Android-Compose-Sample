package leegroup.module.data.util

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
}
