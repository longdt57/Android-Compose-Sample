package leegroup.module.data.local.preferences

import android.content.SharedPreferences
import leegroup.module.data.util.JsonUtil
import kotlin.reflect.KProperty

fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}

fun KProperty<*>.getKey(key: String?): String {
    return key ?: name
}

inline fun <reified T> SharedPreferences.get(key: String): T? =
    if (this.contains(key)) {
        when (T::class) {
            Boolean::class -> this.getBoolean(key, false) as T?
            String::class -> this.getString(key, null) as T?
            Float::class -> this.getFloat(key, 0f) as T?
            Int::class -> this.getInt(key, 0) as T?
            Long::class -> this.getLong(key, 0L) as T?
            else -> this.getString(key, null).takeUnless { it.isNullOrBlank() }?.let {
                JsonUtil.decodeFromString<T>(it)
            }
        }
    } else {
        null
    }

fun SharedPreferences.set(key: String, value: Any?) {
    this.execute {
        if (value == null) {
            it.remove(key)
            return@execute
        }
        when (value) {
            is Boolean -> it.putBoolean(key, value)
            is String -> it.putString(key, value)
            is Float -> it.putFloat(key, value)
            is Long -> it.putLong(key, value)
            is Int -> it.putInt(key, value)
            else -> it.putString(key, JsonUtil.encodeToString(value))
        }
    }
}

