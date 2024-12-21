package com.app.androidcompose.repository

import com.app.androidcompose.MockUtil
import com.app.androidcompose.data.local.room.UserDao
import com.app.androidcompose.data.remote.models.responses.UserResponse
import com.app.androidcompose.data.remote.services.ApiService
import com.app.androidcompose.data.repository.UserRepositoryImpl
import com.app.androidcompose.domain.repositories.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
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
        coEvery { mockUserDao.insertAll(expected) } returns Unit

        repository.getUserRemote().collect {
            it shouldBe MockUtil.userModels
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = RuntimeException()
        coEvery { mockService.getUser() } throws expected

        repository.getUserRemote().catch {
            it shouldBe expected
        }.collect()
    }
}
