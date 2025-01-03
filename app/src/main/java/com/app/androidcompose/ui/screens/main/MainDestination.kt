package com.app.androidcompose.ui.screens.main

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.app.androidcompose.ui.base.BaseDestination
import kotlinx.serialization.Serializable

sealed class MainDestination {
    object GitUserList : BaseDestination("gitUserList")
    object GitUserDetail : BaseDestination("gitUserDetail/{login}") {
        override val arguments = listOf(
            navArgument("login") { type = NavType.StringType }
        )

        // New composable: https://developer.android.com/develop/ui/compose/navigation#deeplinks
        @Serializable
        data class GitUserDetailLogin(val login: String)
    }
}
