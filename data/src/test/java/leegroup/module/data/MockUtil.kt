package leegroup.module.data

import io.mockk.every
import io.mockk.mockk
import leegroup.module.data.local.room.models.UserEntity
import leegroup.module.data.remote.models.responses.User
import leegroup.module.domain.models.Error
import leegroup.module.domain.models.UserModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

object MockUtil {

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

    val users = listOf(
        User(
            id = 1,
            firstName = "Logan",
            lastName = "Do"
        ),
        User(
            id = 2,
            firstName = "Nick",
            lastName = "Do"
        )
    )

    val userModels = listOf(
        UserModel(
            id = 1,
            firstName = "Logan",
            lastName = "Do"
        ),
        UserModel(
            id = 2,
            firstName = "Nick",
            lastName = "Do"
        )
    )
    val userEntities = listOf(
        UserEntity(
            id = 1,
            firstName = "Logan",
            lastName = "Do"
        ),
        UserEntity(
            id = 2,
            firstName = "Nick",
            lastName = "Do"
        )
    )
}