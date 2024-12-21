package com.app.androidcompose.domain.repositories

import com.app.androidcompose.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserRemote(): Flow<List<UserModel>>
    fun getUserLocalAsFlow(): Flow<List<UserModel>>
}