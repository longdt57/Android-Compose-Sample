package leegroup.module.domain.repositories

import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.params.GetGitUserListParam

interface GitUserRepository {
    suspend fun getRemote(param: GetGitUserListParam): List<GitUserModel>
    suspend fun getLocal(param: GetGitUserListParam): List<GitUserModel>
}