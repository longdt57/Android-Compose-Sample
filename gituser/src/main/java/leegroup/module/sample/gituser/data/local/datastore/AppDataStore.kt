package leegroup.module.sample.gituser.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow
import leegroup.module.extension.datastore.BaseDataStore
import javax.inject.Inject
import javax.inject.Singleton

private const val APP_DATASTORE = "app-datastore"

@Singleton
class AppDataStore @Inject constructor(
    context: Context,
) : BaseDataStore(context, APP_DATASTORE) {

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