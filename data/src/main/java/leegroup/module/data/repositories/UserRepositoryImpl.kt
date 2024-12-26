package leegroup.module.data.repositories

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import leegroup.module.data.extensions.flowTransform
import leegroup.module.data.local.room.UserDao
import leegroup.module.data.local.room.models.mapToDomain
import leegroup.module.data.remote.models.responses.User
import leegroup.module.data.remote.models.responses.mapToDomain
import leegroup.module.data.remote.models.responses.mapToEntity
import leegroup.module.data.remote.services.ApiService
import leegroup.module.domain.models.UserModel
import leegroup.module.domain.repositories.UserRepository

class UserRepositoryImpl @Inject constructor(
    private val appService: ApiService,
    private val userDao: UserDao,
) : UserRepository {

    override fun getRemote() = flowTransform {
        val users = appService.getUser().users
        saveToLocal(users)
        users.map { it.mapToDomain() }
    }

    override fun getLocal(): Flow<List<UserModel>> {
        return userDao.getAllAsFlow().map { items -> items.map { it.mapToDomain() } }
    }

    private suspend fun saveToLocal(users: List<User>) {
        userDao.clearTable()
        userDao.upsert(users.map { it.mapToEntity() })
    }
}