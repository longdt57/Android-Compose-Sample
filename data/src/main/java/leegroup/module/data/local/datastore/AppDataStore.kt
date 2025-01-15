package leegroup.module.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import leegroup.module.extension.datastore.BaseDataStore
import javax.inject.Inject

private const val APP_PREFERENCES = "app-datastore"

class AppDataStore @Inject constructor(
    context: Context,
) : BaseDataStore(context, APP_PREFERENCES) {

    fun getAppPreference(): Flow<Boolean?> {
        return getValue(APP_PREFERENCE)
    }

    suspend fun setAppPreference(value: Boolean) {
        setValue(APP_PREFERENCE, value)
    }

    companion object {
        private val APP_PREFERENCE = booleanPreferencesKey("APP_PREFERENCE")
    }
}