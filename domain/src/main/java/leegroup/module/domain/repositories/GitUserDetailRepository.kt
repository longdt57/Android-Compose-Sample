package leegroup.module.domain.repositories

import leegroup.module.domain.models.GitUserDetailModel

interface GitUserDetailRepository {
    suspend fun getRemote(login: String): GitUserDetailModel
    suspend fun getLocal(login: String): GitUserDetailModel?
}