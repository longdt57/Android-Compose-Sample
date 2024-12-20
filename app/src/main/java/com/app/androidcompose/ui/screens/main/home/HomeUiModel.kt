package com.app.androidcompose.ui.screens.main.home

import com.app.androidcompose.data.model.User

data class HomeUiModel(
    val users: List<User> = emptyList()
)
