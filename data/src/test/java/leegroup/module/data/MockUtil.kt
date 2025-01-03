package leegroup.module.data

import io.mockk.every
import io.mockk.mockk
import leegroup.module.data.models.GitUser
import leegroup.module.data.models.GitUserDetail
import leegroup.module.domain.models.Error
import leegroup.module.domain.models.GitUserDetailModel
import leegroup.module.domain.models.GitUserModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object MockUtil {
    const val GIT_USER_DETAIL_LOGIN = "longdt57"
    const val GIT_USER_SINCE = 0
    const val GIT_USER_PER_PAGE = 3

    val mockHttpException: HttpException
        get() {
            val response = mockk<Response<Any>>()
            val httpException = mockk<HttpException>()
            val responseBody = mockk<ResponseBody>()
            every { response.code() } returns 500
            every { response.message() } returns "message"
            every { response.errorBody() } returns responseBody
            every { httpException.code() } returns response.code()
            every { httpException.message() } returns response.message()
            every { httpException.response() } returns response
            every { responseBody.string() } returns
                    """
                    {
                        "message": "message"
                    }
                """.trimIndent()
            return httpException
        }

    val error = Error(message = "message")

    val gitUserDetail = GitUserDetail(
        id = 8809113,
        login = GIT_USER_DETAIL_LOGIN,
        name = "Logan Do",
        avatarUrl = "https://avatars.githubusercontent.com/u/8809113?v=4",
        blog = "https://github.com/longdt57",
        location = "Hanoi",
        followers = 100_000,
        following = 50_000
    )

    val gitUserDetailModel = GitUserDetailModel(
        id = 8809113,
        login = GIT_USER_DETAIL_LOGIN,
        name = "Logan Do",
        avatarUrl = "https://avatars.githubusercontent.com/u/8809113?v=4",
        blog = "https://github.com/longdt57",
        location = "Hanoi",
        followers = 100_000,
        following = 50_000,
    )

    val sampleGitUsers = listOf(
        GitUser(
            id = 1,
            login = "longdt57",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            htmlUrl = "https://github.com/longdt57"
        ),
        GitUser(
            id = 2,
            login = "defunkt",
            avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
            htmlUrl = "https://github.com/defunkt"
        ),
        GitUser(
            id = 3,
            login = "pjhyett",
            avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
            htmlUrl = "https://github.com/pjhyett"
        )
    )

    val sampleGitUserModels = listOf(
        GitUserModel(
            id = 1,
            login = "longdt57",
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            htmlUrl = "https://github.com/longdt57"
        ),
        GitUserModel(
            id = 2,
            login = "defunkt",
            avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
            htmlUrl = "https://github.com/defunkt"
        ),
        GitUserModel(
            id = 3,
            login = "pjhyett",
            avatarUrl = "https://avatars.githubusercontent.com/u/3?v=4",
            htmlUrl = "https://github.com/pjhyett"
        )
    )
}