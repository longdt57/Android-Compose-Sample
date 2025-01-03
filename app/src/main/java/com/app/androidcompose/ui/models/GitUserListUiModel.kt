package com.app.androidcompose.ui.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import leegroup.module.domain.models.GitUserModel

@Immutable
data class GitUserListUiModel(
    val users: ImmutableList<GitUserModel> = persistentListOf(),
)
