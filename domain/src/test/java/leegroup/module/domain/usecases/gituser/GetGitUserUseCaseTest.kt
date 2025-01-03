package leegroup.module.domain.usecases.gituser

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import leegroup.module.domain.models.GitUserModel
import leegroup.module.domain.repositories.GitUserRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetGitUserUseCaseTest {

    private lateinit var repository: GitUserRepository
    private lateinit var getGitUserUseCase: GetGitUserUseCase

    private val gitUserModels = listOf(
        GitUserModel(
            1,
            "longdt57",
            "https://avatars.githubusercontent.com/u/8809113?v=4",
            "https://github.com/longdt57"
        ),
        GitUserModel(2, "user2", "avatar2", "htmlUrl2")
    )

    @Before
    fun setUp() {
        repository = mockk()
        getGitUserUseCase = GetGitUserUseCase(repository)
    }

    @Test
    fun `should return local data when local is not empty`() = runTest {
        coEvery { repository.getLocal(0, 2) } returns gitUserModels

        val result = getGitUserUseCase(0, 2).first()

        assertEquals(gitUserModels, result)
        coVerify(exactly = 1) { repository.getLocal(0, 2) }
        coVerify(exactly = 0) { repository.getRemote(0, 2) }
    }

    @Test
    fun `should fetch remote data when local is empty`() = runTest {
        coEvery { repository.getLocal(0, 2) } returns emptyList()
        coEvery { repository.getRemote(0, 2) } returns gitUserModels

        val result = getGitUserUseCase(0, 2).first()

        assertEquals(gitUserModels, result)
        coVerify(exactly = 1) { repository.getLocal(0, 2) }
        coVerify(exactly = 1) { repository.getRemote(0, 2) }
    }
}
