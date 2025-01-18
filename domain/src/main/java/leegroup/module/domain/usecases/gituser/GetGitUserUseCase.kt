package leegroup.module.domain.usecases.gituser

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.params.GetGitUserListParam
import leegroup.module.domain.repositories.GitUserRepository
import javax.inject.Inject

class GetGitUserUseCase @Inject constructor(
    private val repository: GitUserRepository
) {

    operator fun invoke(param: GetGitUserListParam): Flow<List<GitUserModel>> {
        return flow {
            val localData = repository.getLocal(param)
            if (localData.isNotEmpty()) {
                emit(localData)
            } else {
                emit(repository.getRemote(param))
            }
        }
    }
}