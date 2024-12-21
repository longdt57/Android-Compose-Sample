package com.app.androidcompose.ui.screens.main.home

import com.app.androidcompose.domain.model.UserModel

data class HomeUiModel(
    val users: List<UserModel> = emptyList()
)
