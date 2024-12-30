package leegroup.module.data.repositories

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import leegroup.module.data.extensions.flowTransform
import leegroup.module.data.local.room.UserDao
import leegroup.module.data.local.room.models.UserEntity
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
        mapFromPojoToDomain(users)
    }

    override fun getLocal(): Flow<List<UserModel>> {
        return userDao.getAllAsFlow().map { users -> mapFromEntityToDomain(users) }
    }

    private suspend fun saveToLocal(users: List<User>) {
        userDao.clearTable()
        userDao.upsert(mapToEntity(users))
    }

    private fun mapFromPojoToDomain(users: List<User>): List<UserModel> {
        return users.map { it.mapToDomain() }
    }

    private fun mapFromEntityToDomain(users: List<UserEntity>): List<UserModel> {
        return users.map { it.mapToDomain() }
    }

    private fun mapToEntity(users: List<User>): List<UserEntity> {
        return users.map { it.mapToEntity() }
    }
}