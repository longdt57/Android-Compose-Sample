package com.app.androidcompose.ui.screens.main.gituser

sealed interface GitUserListAction {
    data object LoadIfEmpty : GitUserListAction
    data object LoadMore : GitUserListAction
    data object TrackLaunch : GitUserListAction
    data class TrackOpenUserDetail(val login: String) : GitUserListAction
}