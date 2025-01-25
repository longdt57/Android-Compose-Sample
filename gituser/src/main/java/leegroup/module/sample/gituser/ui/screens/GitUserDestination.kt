package leegroup.module.sample.gituser.ui.screens

import kotlinx.serialization.Serializable
import leegroup.module.compose.ui.models.BaseDestination

sealed class GitUserDestination {
    object GitUserRoot : BaseDestination("gitUserRoot")

    object GitUserList : BaseDestination("gitUserList")
    object GitUserDetail : BaseDestination("") {

        @Serializable
        data class GitUserDetailNav(
            val login: String
        )
    }
}
