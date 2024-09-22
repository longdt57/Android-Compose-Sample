package com.app.androidcompose.domain.usecases.user

import com.app.androidcompose.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserRemoteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.getUserRemote()
}
