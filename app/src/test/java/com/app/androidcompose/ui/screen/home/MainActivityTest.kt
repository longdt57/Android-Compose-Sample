package com.app.androidcompose.ui.screen.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.app.androidcompose.MainActivity
import com.app.androidcompose.MockUtil
import com.app.androidcompose.R
import com.app.androidcompose.support.BaseScreenTest
import com.app.androidcompose.ui.base.BaseDestination
import com.app.androidcompose.ui.screens.main.home.HomeScreen
import com.app.androidcompose.ui.screens.main.home.HomeViewModel
import com.app.androidcompose.ui.theme.ComposeTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import leegroup.module.domain.exceptions.NoConnectivityException
import leegroup.module.domain.usecases.user.GetUserRemoteUseCase
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockUseCase: GetUserRemoteUseCase = mockk()

    private lateinit var viewModel: HomeViewModel
    private var expectedDestination: BaseDestination? = null

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(MockUtil.userModels)
    }

    @Test
    fun `When entering the Home screen, it shows UI correctly`() = initComposable {
        onNodeWithText(activity.getString(R.string.app_name)).assertIsDisplayed()
    }

    @Test
    @Ignore("Can't test dialog")
    fun `When entering the Home screen and loading the data failure, it shows the corresponding error`() {
        setStandardTestDispatcher()

        val error = NoConnectivityException
        every { mockUseCase() } returns flow { throw error }

        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()
            onNodeWithText(activity.getString(R.string.popup_error_no_connection_title)).assertIsDisplayed()
        }
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel()

        composeRule.activity.setContent {
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                    navigator = { destination -> expectedDestination = destination }
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            coroutinesRule.testDispatcherProvider,
            mockUseCase,
        )
    }
}
