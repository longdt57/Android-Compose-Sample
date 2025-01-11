package com.app.androidcompose.ui.screens.main

import leegroup.module.compose.ui.models.BaseDestination
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
