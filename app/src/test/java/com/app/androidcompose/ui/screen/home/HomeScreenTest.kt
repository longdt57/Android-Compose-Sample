package com.app.androidcompose.ui.screen.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.app.androidcompose.MockUtil
import com.app.androidcompose.support.BaseScreenTest
import com.app.androidcompose.ui.screens.main.home.UserContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `Test User Content`() {
        val user = MockUtil.users.first()
        composeRule.setContent {
            UserContent(user)
        }
        composeRule.onNodeWithText("${user.firstName} ${user.lastName}").assertIsDisplayed()
    }
}
