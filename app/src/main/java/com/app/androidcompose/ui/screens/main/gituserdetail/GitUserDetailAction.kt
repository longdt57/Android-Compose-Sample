package com.app.androidcompose.ui.screens.main.gituserdetail

sealed interface GitUserDetailAction {
    data class SetUserLogin(val login: String) : GitUserDetailAction
}
