package leegroup.module.domain.usecases

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import leegroup.module.domain.MockUtil
import leegroup.module.domain.exceptions.NoConnectivityException
import leegroup.module.domain.exceptions.ServerException
import leegroup.module.domain.repositories.UserRepository
import leegroup.module.domain.usecases.user.GetUserRemoteUseCase
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserRemoteUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var useCase: GetUserRemoteUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        useCase = GetUserRemoteUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.userModels
        every { mockRepository.getRemote() } returns flowOf(expected)

        useCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = MockUtil.apiException
        every { mockRepository.getRemote() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }

    @Test
    fun `When request failed, it returns ServerException`() = runTest {
        val expected = ServerException
        every { mockRepository.getRemote() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }

    @Test
    fun `When request failed, it returns NoConnectivityException`() = runTest {
        val expected = NoConnectivityException
        every { mockRepository.getRemote() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }
}