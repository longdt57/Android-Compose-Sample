package leegroup.module.data.remote.services

import leegroup.module.data.models.GitUser
import leegroup.module.data.models.GitUserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getGitUser(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): List<GitUser>

    @GET("users/{login}")
    suspend fun getGitUserDetail(
        @Path("login") login: String,
    ): GitUserDetail
}
