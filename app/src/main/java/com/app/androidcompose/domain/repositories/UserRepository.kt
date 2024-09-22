package com.app.androidcompose.domain.repositories

import com.app.androidcompose.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserRemote(): Flow<List<User>>
    fun getUserLocalAsFlow(): Flow<List<User>>
}