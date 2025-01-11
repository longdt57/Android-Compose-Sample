package com.app.androidcompose.ui.screen.gituserdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.app.androidcompose.MockUtil
import com.app.androidcompose.support.CoroutineTestRule
import com.app.androidcompose.ui.mapper.GitUserDetailUiMapper
import com.app.androidcompose.ui.models.GitUserDetailUiModel
import com.app.androidcompose.ui.screens.main.MainDestination
import com.app.androidcompose.ui.screens.main.gituserdetail.GitUserDetailViewModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import leegroup.module.compose.ui.models.ErrorState
import leegroup.module.domain.models.GitUserDetailModel
import leegroup.module.domain.usecases.gituser.GetGitUserDetailLocalUseCase
import leegroup.module.domain.usecases.gituser.GetGitUserDetailRemoteUseCase
import leegroup.module.extension.JsonUtil
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GitUserDetailViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private lateinit var mockSavedStateHandle: SavedStateHandle
    private val mockLocalUseCase: GetGitUserDetailLocalUseCase = mockk()
    private val mockRemoteUseCase: GetGitUserDetailRemoteUseCase = mockk()
    private val mockUiMapper: GitUserDetailUiMapper = mockk()

    private lateinit var viewModel: GitUserDetailViewModel

    private val userLogin = "longdt57"

    private val gitUserDetailModel = GitUserDetailModel(
        id = 1,
        login = userLogin,
        name = "Long DT",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        blog = "https://longdt57.com",
        location = "Vietnam",
        followers = 1500,
        following = 100
    )

    private val gitUserDetailUiModel = GitUserDetailUiModel(
        login = userLogin,
        name = "Long DT",
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        blog = "https://longdt57.com",
        location = "Vietnam",
        followers = "999+",
        following = "100"
    )

    @Before
    fun setUp() {
        val nav = MainDestination.GitUserDetail.GitUserDetailNav(userLogin)
        mockSavedStateHandle = SavedStateHandle(JsonUtil.encodeToMap(nav))
        every { mockUiMapper.mapToUiModel(any(), any()) } returns gitUserDetailUiModel
    }

    private fun initViewModel() {
        viewModel = GitUserDetailViewModel(
            savedStateHandle = mockSavedStateHandle,
            coroutinesRule.testDispatcherProvider,
            mockLocalUseCase,
            mockRemoteUseCase,
            mockUiMapper
        )
    }

    @Test
    fun `When setting user login, it fetches local and remote data`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf(gitUserDetailModel)
        every { mockRemoteUseCase(userLogin) } returns flowOf(gitUserDetailModel)

        initViewModel()

        viewModel.uiModel.test {
            val updatedModel = expectMostRecentItem()
            assertEquals(
                "Expected UI model to match mapped UI model",
                gitUserDetailUiModel,
                updatedModel
            )
        }

        verify { mockLocalUseCase(userLogin) }
        verify { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When remote fetch fails and no local data, it shows error state`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf()
        every { mockRemoteUseCase(userLogin) } returns flow { throw RuntimeException("Remote fetch error") }

        initViewModel()

        viewModel.error.test {
            val errorState = expectMostRecentItem()
            assertTrue("Expected error state to be set", errorState is ErrorState.Common)
        }

        verify { mockLocalUseCase(userLogin) }
        verify { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When local return error, it doesn't show error state`() = runTest {
        every { mockRemoteUseCase(userLogin) } returns flowOf(gitUserDetailModel)
        every { mockLocalUseCase(userLogin) } returns flow { throw RuntimeException("Remote fetch error") }

        initViewModel()

        viewModel.error.test {
            val errorState = expectMostRecentItem()
            assertTrue("Expected error state not to be set", errorState is ErrorState.None)
        }

        verify { mockLocalUseCase(userLogin) }
        verify { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When local succeeds, it updates the UI model correctly`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf(gitUserDetailModel)
        every { mockRemoteUseCase(userLogin) } returns flow { throw RuntimeException("Remote fetch error") }

        initViewModel()

        viewModel.uiModel.test {
            val updatedModel = expectMostRecentItem()
            assertEquals(
                "Expected UI model to match mapped UI model",
                gitUserDetailUiModel,
                updatedModel
            )
        }
        verify { mockLocalUseCase(userLogin) }
        verify { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When local and remote both succeed, remote data takes precedence`() = runTest {
        val remoteModel = gitUserDetailModel.copy(name = "Remote Long DT")
        val remoteUiModel = gitUserDetailUiModel.copy(name = "Remote Long DT")

        every { mockLocalUseCase(userLogin) } returns flowOf(gitUserDetailModel)
        every { mockRemoteUseCase(userLogin) } returns flowOf(remoteModel)
        every { mockUiMapper.mapToUiModel(any(), eq(remoteModel)) } returns remoteUiModel

        initViewModel()

        viewModel.uiModel.test {
            val updatedModel = expectMostRecentItem()
            assertEquals("Expected UI model to use remote data", remoteUiModel, updatedModel)
        }

        verify { mockLocalUseCase(userLogin) }
        verify { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When Api onErrorConfirmation is called, it calls fetchRemote again`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf()
        every { mockRemoteUseCase(userLogin) } returns flow {
            MockUtil.noConnectivityException
        }

        initViewModel()
        viewModel.onErrorConfirmation(MockUtil.apiErrorState)
        verify(exactly = 2) { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When Network onErrorConfirmation is called, it calls fetchRemote again`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf()
        every { mockRemoteUseCase(userLogin) } returns flow {
            throw MockUtil.noConnectivityException
        }

        initViewModel()
        viewModel.onErrorConfirmation(ErrorState.Network())
        verify(exactly = 2) { mockRemoteUseCase(userLogin) }
    }

    @Test
    fun `When Api onDismissClick is called, it hides error`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf()
        every { mockRemoteUseCase(userLogin) } returns flow {
            throw MockUtil.noConnectivityException
        }

        initViewModel()
        viewModel.onErrorDismissClick(MockUtil.apiErrorState)
        viewModel.error.test {
            expectMostRecentItem() shouldBe ErrorState.None
        }
    }

    @Test
    fun `When Common onErrorConfirmation is called, it doesn't call fetchRemote`() = runTest {
        every { mockLocalUseCase(userLogin) } returns flowOf()
        every { mockRemoteUseCase(userLogin) } returns flow {
            throw RuntimeException()
        }

        initViewModel()
        viewModel.onErrorConfirmation(ErrorState.Common)
        verify(exactly = 1) { mockRemoteUseCase(userLogin) }
    }
}
