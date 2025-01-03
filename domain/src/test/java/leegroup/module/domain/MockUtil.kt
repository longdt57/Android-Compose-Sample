package leegroup.module.domain

import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.models.Error
import leegroup.module.domain.models.GitUserDetailModel

object MockUtil {
    const val GIT_USER_DETAIL_LOGIN = "longdt57"

    val apiException = ApiException(
        error = Error(
            message = "message",
        ),
        httpCode = 400,
        httpMessage = "Bad Request "
    )

    val gitUserDetail = GitUserDetailModel(
        id = 8809113,
        login = GIT_USER_DETAIL_LOGIN,
        name = "Logan Do",
        avatarUrl = "https://avatars.githubusercontent.com/u/8809113?v=4",
        blog = "https://github.com/longdt57",
        location = "Hanoi",
        followers = 100_000,
        following = 50_000,
    )
}