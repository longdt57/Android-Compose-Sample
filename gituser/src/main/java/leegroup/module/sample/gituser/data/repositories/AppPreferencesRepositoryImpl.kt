package leegroup.module.sample.gituser.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import leegroup.module.domain.repositories.AppPreferencesRepository
import leegroup.module.sample.gituser.data.local.datastore.AppDataStore
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject internal constructor(
    private val appDataStore: AppDataStore
) : AppPreferencesRepository {

    override fun getAppPreference(): Flow<Boolean> {
        return appDataStore.getAppPreference()
            .map { it ?: true }
    }

    override suspend fun updateAppPreference(appPreferencesValue: Boolean) {
        appDataStore.setAppPreference(appPreferencesValue)
    }
}
