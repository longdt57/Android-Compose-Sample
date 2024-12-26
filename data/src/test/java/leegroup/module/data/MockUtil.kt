package leegroup.module.data

import leegroup.module.data.remote.models.responses.User
import leegroup.module.data.remote.models.responses.mapToDomain
import leegroup.module.data.remote.models.responses.ErrorResponse
import io.mockk.every
import io.mockk.mockk
import leegroup.module.data.remote.models.responses.mapToEntity
import leegroup.module.data.remote.models.responses.mapToError
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
                        "message": "message",
                        "code": 1
                    }
                """.trimIndent()
            return httpException
        }

    val errorResponse = ErrorResponse(
        message = "message",
        code = 1
    )

    val error = errorResponse.mapToError()

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

    val userModels = users.map { it.mapToDomain() }
    val userEntities = users.map { it.mapToEntity() }
}