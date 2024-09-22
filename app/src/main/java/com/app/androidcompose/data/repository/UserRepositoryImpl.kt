package com.app.androidcompose.data.repository

import com.app.androidcompose.data.extensions.flowTransform
import com.app.androidcompose.data.model.User
import com.app.androidcompose.data.remote.services.ApiService
import com.app.androidcompose.data.local.room.UserDao
import com.app.androidcompose.domain.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val appService: ApiService,
    private val userDao: UserDao,
) : UserRepository {

    override fun getUserRemote() = flowTransform {
        val users = appService.getUser().users
        userDao.clearTable()
        userDao.insertAll(users)
        users
    }

    override fun getUserLocalAsFlow(): Flow<List<User>> {
        return userDao.getAllAsFlow()
    }
}