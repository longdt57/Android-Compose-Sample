package leegroup.module.data.repositories

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import leegroup.module.data.MockUtil
import leegroup.module.data.local.room.UserDao
import leegroup.module.data.remote.models.responses.mapToEntity
import leegroup.module.data.remote.models.responses.UserResponse
import leegroup.module.data.remote.services.ApiService
import leegroup.module.domain.repositories.UserRepository
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var mockUserDao: UserDao
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        mockService = mockk()
        mockUserDao = mockk()
        repository = UserRepositoryImpl(mockService, mockUserDao)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.users
        coEvery { mockService.getUser() } returns UserResponse(expected)
        coEvery { mockUserDao.clearTable() } returns Unit
        coEvery { mockUserDao.upsert(expected.map { it.mapToEntity() }) } returns Unit

        repository.getRemote().collect {
            it shouldBe MockUtil.userModels
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = RuntimeException()
        coEvery { mockService.getUser() } throws expected

        repository.getRemote().catch {
            it shouldBe expected
        }.collect()
    }

    @Test
    fun `getLocal should return transformed local data`() = runTest {
        val localEntities = MockUtil.userEntities
        coEvery { mockUserDao.getAllAsFlow() } returns flowOf(localEntities)

        repository.getLocal().collect { result ->
            result shouldBe MockUtil.userModels
        }
    }

    @Test
    fun `saveToLocal should clear table and upsert transformed data`() = runTest {
        coEvery { mockService.getUser() } returns UserResponse(MockUtil.users)
        coEvery { mockUserDao.clearTable() } returns Unit
        coEvery { mockUserDao.upsert(MockUtil.userEntities) } returns Unit

        repository.getRemote().collect()

        coVerify(exactly = 1) { mockUserDao.clearTable() }
        coVerify(exactly = 1) { mockUserDao.upsert(MockUtil.userEntities) }
    }

    @Test
    fun `getRemote should handle empty list from API`() = runTest {
        coEvery { mockService.getUser() } returns UserResponse(emptyList())
        coEvery { mockUserDao.clearTable() } returns Unit
        coEvery { mockUserDao.upsert(emptyList()) } returns Unit

        repository.getRemote().collect { result ->
            result shouldBe emptyList()
        }
    }

    @Test
    fun `getLocal should return empty list when database is empty`() = runTest {
        coEvery { mockUserDao.getAllAsFlow() } returns flowOf(emptyList())

        repository.getLocal().collect { result ->
            result shouldBe emptyList()
        }
    }
}
