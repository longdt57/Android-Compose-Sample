package leegroup.module.data.repositories

import leegroup.module.data.extensions.transform
import leegroup.module.data.local.room.GitUserDao
import leegroup.module.data.models.GitUser
import leegroup.module.data.models.mapToDomain
import leegroup.module.data.remote.services.ApiService
import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.repositories.GitUserRepository
import javax.inject.Inject

class GitUserRepositoryImpl @Inject constructor(
    private val appService: ApiService,
    private val userDao: GitUserDao,
) : GitUserRepository {

    override suspend fun getRemote(since: Int, perPage: Int) = transform {
        val users = appService.getGitUser(since = since, perPage = perPage)
        saveToLocal(users)
        mapToDomain(users)
    }

    override suspend fun getLocal(since: Int, perPage: Int): List<GitUserModel> {
        return userDao
            .getUsers(since = since, perPage = perPage)
            .let { mapToDomain(it) }
    }

    private suspend fun saveToLocal(users: List<GitUser>) {
        userDao.upsert(users)
    }

    private fun mapToDomain(users: List<GitUser>): List<GitUserModel> {
        return users.mapToDomain()
    }
}