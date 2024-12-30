package com.app.androidcompose

import com.app.androidcompose.ui.base.ErrorState
import leegroup.module.data.remote.models.responses.ErrorResponse
import leegroup.module.data.remote.models.responses.User
import leegroup.module.data.remote.models.responses.mapToDomain
import leegroup.module.data.remote.models.responses.mapToError
import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.models.Error

object MockUtil {

    private const val API_ERROR_MESSAGE = "Something went wrong"

    val apiErrorState = ErrorState.Api(message = API_ERROR_MESSAGE)

    val apiError = ApiException(
        error = Error(message = API_ERROR_MESSAGE),
        httpCode = 404,
        httpMessage = "Not Found"
    )

    val errorResponse = ErrorResponse(
        message = "message"
    )

    val errorD = errorResponse.mapToError()

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