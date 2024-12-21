package com.app.androidcompose.ui.screen.home

import app.cash.turbine.test
import com.app.androidcompose.MockUtil
import com.app.androidcompose.domain.exceptions.NoConnectivityException
import com.app.androidcompose.domain.usecases.user.GetUserRemoteUseCase
import com.app.androidcompose.support.CoroutineTestRule
import com.app.androidcompose.ui.base.LoadingState
import com.app.androidcompose.ui.screens.main.home.HomeViewModel
import com.app.androidcompose.support.util.DispatchersProvider
import com.app.androidcompose.ui.base.ErrorState
import com.app.androidcompose.ui.screens.main.home.HomeUiModel
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockUseCase: GetUserRemoteUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(MockUtil.userModels)

        initViewModel()
    }

    @Test
    fun `When loading models successfully, it shows the model list`() = runTest {
        viewModel.uiModels.test {
            expectMostRecentItem() shouldBe HomeUiModel(users = MockUtil.userModels)
        }
    }

    @Test
    fun `When loading models failed, it shows the corresponding error`() = runTest {
        val error = NoConnectivityException
        every { mockUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.error.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe ErrorState.Network()
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.loading.test {
            awaitItem() shouldBe LoadingState.None
            awaitItem() shouldBe LoadingState.Loading()
            awaitItem() shouldBe LoadingState.None
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = HomeViewModel(
            dispatchers,
            mockUseCase
        )
    }
}
