package com.app.androidcompose

import com.app.androidcompose.data.model.User
import com.app.androidcompose.data.model.mapToDomain
import com.app.androidcompose.data.remote.models.responses.ErrorResponse
import io.mockk.every
import io.mockk.mockk
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
            every { responseBody.string() } returns "{\n" +
                "  \"message\": \"message\"\n" +
                "}"
            return httpException
        }

    val errorResponse = ErrorResponse(
        message = "message"
    )

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
}