package com.app.androidcompose.usecase

import com.app.androidcompose.MockUtil
import com.app.androidcompose.domain.repositories.UserRepository
import com.app.androidcompose.domain.usecases.user.GetUserRemoteUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
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
        val expected = MockUtil.users
        coEvery { mockRepository.getUserRemote() } returns flowOf(expected)

        useCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.getUserRemote() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }
}