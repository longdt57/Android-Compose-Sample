package leegroup.module.domain.usecases.user

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import leegroup.module.domain.models.UserModel
import leegroup.module.domain.repositories.UserRepository
import leegroup.module.domain.usecases.StrategyUseCase

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : StrategyUseCase<Unit, List<UserModel>>() {

    override fun getRemote(param: Unit): Flow<List<UserModel>> {
        return userRepository.getRemote()
    }

    override fun getLocal(param: Unit): Flow<List<UserModel>> {
        return userRepository.getLocal()
    }
}