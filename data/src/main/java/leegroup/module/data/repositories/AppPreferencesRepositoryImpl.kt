package leegroup.module.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import leegroup.module.data.local.datastore.AppDataStore
import leegroup.module.data.local.datastore.PreferenceKey.APP_PREFERENCE
import leegroup.module.domain.repositories.AppPreferencesRepository
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject internal constructor(
    private val appDataStore: AppDataStore
) : AppPreferencesRepository {

    override fun getAppPreference(): Flow<Boolean> {
        return appDataStore.getValue(APP_PREFERENCE).map { it ?: true }
    }

    override suspend fun updateAppPreference(appPreferencesValue: Boolean) {
        appDataStore.setValue(APP_PREFERENCE, appPreferencesValue)
    }
}
