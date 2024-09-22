package com.app.androidcompose.data.remote.services

import com.app.androidcompose.data.remote.models.responses.UserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("https://dummyjson.com/users")
    suspend fun getUser(): UserResponse
}
