package com.app.androidcompose.domain.usecases.user

import com.app.androidcompose.domain.model.UserModel
import com.app.androidcompose.domain.repositories.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetUserLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<List<UserModel>> {
        return userRepository.getUserLocalAsFlow()
    }
}