package leegroup.module.data.remote.services

import leegroup.module.data.remote.models.responses.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("https://dummyjson.com/users")
    suspend fun getUser(): UserResponse
}
