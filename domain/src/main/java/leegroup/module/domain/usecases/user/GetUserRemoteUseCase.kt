package leegroup.module.domain.usecases.user

import leegroup.module.domain.repositories.UserRepository
import javax.inject.Inject

class GetUserRemoteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke() = userRepository.getRemote()
}
