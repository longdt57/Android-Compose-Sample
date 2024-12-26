package com.app.androidcompose.ui.screens.main.home

import leegroup.module.domain.models.UserModel

data class HomeUiModel(
    val users: List<UserModel> = emptyList()
)
