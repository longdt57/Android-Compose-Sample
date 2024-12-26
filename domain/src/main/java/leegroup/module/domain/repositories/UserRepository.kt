package leegroup.module.domain.repositories

import kotlinx.coroutines.flow.Flow
import leegroup.module.domain.models.UserModel

interface UserRepository {
    fun getRemote(): Flow<List<UserModel>>
    fun getLocal(): Flow<List<UserModel>>
}