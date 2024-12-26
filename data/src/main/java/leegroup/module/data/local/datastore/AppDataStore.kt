package leegroup.module.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AppDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    fun <T> getListValue(key: Preferences.Key<String>): Flow<List<T>> {
        return dataStore.data.map { preferences ->
            val value = preferences[key] ?: return@map emptyList()
            Json.decodeFromString<List<T>>(value)
        }
    }

    suspend fun <T> setValue(key: Preferences.Key<String>, value: List<T>) {
        dataStore.edit { settings ->
            val jsonValue = Json.encodeToString(value)
            settings[key] = jsonValue
        }
    }
}
