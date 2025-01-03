package leegroup.module.domain.usecases.gituser

import kotlinx.coroutines.flow.flow
import leegroup.module.domain.repositories.GitUserDetailRepository
import javax.inject.Inject

class GetGitUserDetailLocalUseCase @Inject constructor(
    private val repository: GitUserDetailRepository
) {

    operator fun invoke(login: String) = flow {
        repository.getLocal(login)?.let {
            emit(it)
        }
    }
}
