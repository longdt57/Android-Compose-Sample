package leegroup.module.data.repositories

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import leegroup.module.data.MockUtil
import leegroup.module.data.local.room.GitUserDao
import leegroup.module.data.remote.services.ApiService
import leegroup.module.domain.repositories.GitUserRepository
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GitUserRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var mockUserDao: GitUserDao
    private lateinit var repository: GitUserRepository

    private val since = MockUtil.GIT_USER_SINCE
    private val perPage = MockUtil.GIT_USER_PER_PAGE
    private val sampleGitUsers = MockUtil.sampleGitUsers
    private val sampleGitUserModels = MockUtil.sampleGitUserModels

    @Before
    fun setUp() {
        mockService = mockk()
        mockUserDao = mockk()
        repository = GitUserRepositoryImpl(mockService, mockUserDao)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = sampleGitUsers
        coEvery { mockService.getGitUser(since = since, perPage = perPage) } returns expected
        coEvery { mockUserDao.upsert(expected) } returns Unit

        repository.getRemote(since = since, perPage = perPage) shouldBe sampleGitUserModels
        coVerify(exactly = 1) { mockUserDao.upsert(expected) }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = RuntimeException()
        coEvery { mockService.getGitUser(since = since, perPage = perPage) } throws expected

        try {
            repository.getRemote(since = since, perPage = perPage)
        } catch (ex: Exception) {
            ex shouldBe expected
        }
    }

    @Test
    fun `getLocal should return transformed local data`() = runTest {
        val expected = sampleGitUsers
        coEvery { mockUserDao.getUsers(since = since, perPage = perPage) } returns expected

        repository.getLocal(since = since, perPage = perPage) shouldBe sampleGitUserModels
    }

    @Test
    fun `getLocal should return empty when database is empty`() = runTest {
        coEvery { mockUserDao.getUsers(since = since, perPage = perPage) } returns emptyList()

        repository.getLocal(since = since, perPage = perPage) shouldBe emptyList()
    }
}
