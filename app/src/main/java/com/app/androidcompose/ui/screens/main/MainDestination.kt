package com.app.androidcompose.ui.screens.main

import com.app.androidcompose.ui.base.BaseDestination
import kotlinx.serialization.Serializable

sealed class MainDestination {
    object GitUserList : BaseDestination("gitUserList")
    object GitUserDetail : BaseDestination("") {

        @Serializable
        data class GitUserDetailNav(
            val login: String
        )
    }
}
