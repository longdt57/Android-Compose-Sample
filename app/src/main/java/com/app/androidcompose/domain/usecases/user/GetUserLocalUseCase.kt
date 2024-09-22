package com.app.androidcompose.domain.usecases.user

import com.app.androidcompose.data.model.User
import com.app.androidcompose.domain.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<List<User>> {
        return userRepository.getUserLocalAsFlow()
    }
}