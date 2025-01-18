package leegroup.module.sample.gituser.data.repositories

import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.params.GetGitUserListParam
import leegroup.module.domain.repositories.GitUserRepository
import leegroup.module.sample.gituser.data.local.room.GitUserDao
import leegroup.module.sample.gituser.data.models.GitUser
import leegroup.module.sample.gituser.data.models.mapToDomain
import leegroup.module.sample.gituser.data.remote.services.ApiService
import javax.inject.Inject

class GitUserRepositoryImpl @Inject constructor(
    private val appService: ApiService,
    private val userDao: GitUserDao,
) : GitUserRepository {

    override suspend fun getRemote(param: GetGitUserListParam): List<GitUserModel> {
        val users = appService.getGitUser(since = param.since, perPage = param.perPage)
        saveToLocal(users)
        return mapToDomain(users)
    }

    override suspend fun getLocal(param: GetGitUserListParam): List<GitUserModel> {
        return userDao
            .getUsers(since = param.since, perPage = param.perPage)
            .let { mapToDomain(it) }
    }

    private suspend fun saveToLocal(users: List<GitUser>) {
        userDao.upsert(users)
    }

    private fun mapToDomain(users: List<GitUser>): List<GitUserModel> {
        return users.mapToDomain()
    }
}