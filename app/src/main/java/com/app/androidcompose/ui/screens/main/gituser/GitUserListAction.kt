package com.app.androidcompose.ui.screens.main.gituser

sealed interface GitUserListAction {
    data object LoadIfEmpty : GitUserListAction
    data object LoadMore : GitUserListAction
}