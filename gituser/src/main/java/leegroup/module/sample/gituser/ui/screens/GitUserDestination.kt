package leegroup.module.sample.gituser.ui.screens

import kotlinx.serialization.Serializable
import leegroup.module.compose.ui.models.BaseDestination

sealed class GitUserDestination {
    object GitUserRoot : BaseDestination("gitUserRoot")
    internal object GitUserList : BaseDestination("gitUserList")
    internal object GitUserDetail : BaseDestination("") {

        @Serializable
        internal data class GitUserDetailNav(
            val login: String
        )
    }
}
