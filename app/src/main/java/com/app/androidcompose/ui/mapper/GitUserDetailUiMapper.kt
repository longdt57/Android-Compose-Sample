package com.app.androidcompose.ui.mapper

import android.content.Context
import com.app.androidcompose.support.extensions.stringNotSet
import com.app.androidcompose.ui.mapper.util.FollowerFormatter
import com.app.androidcompose.ui.models.GitUserDetailUiModel
import leegroup.module.domain.models.GitUserDetailModel
import javax.inject.Inject

interface GitUserDetailUiMapper {
    fun mapToUiModel(
        oldUiModel: GitUserDetailUiModel,
        model: GitUserDetailModel
    ): GitUserDetailUiModel
}

class GitUserDetailUiMapperImpl @Inject constructor(
    private val context: Context
) : GitUserDetailUiMapper {

    override fun mapToUiModel(
        oldUiModel: GitUserDetailUiModel,
        model: GitUserDetailModel
    ): GitUserDetailUiModel {
        return oldUiModel.copy(
            name = model.name ?: model.login,
            avatarUrl = model.avatarUrl.orEmpty(),
            blog = model.blog.orEmpty(),
            location = model.location.takeUnless { it.isNullOrBlank() } ?: context.stringNotSet(),
            followers = FollowerFormatter.formatLargeNumber(model.followers),
            following = FollowerFormatter.formatLargeNumber(model.following),
        )
    }
}