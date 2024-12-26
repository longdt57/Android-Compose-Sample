package com.app.androidcompose

import leegroup.module.data.remote.models.responses.User
import leegroup.module.data.remote.models.responses.mapToDomain
import leegroup.module.data.remote.models.responses.ErrorResponse
import leegroup.module.data.remote.models.responses.mapToError

object MockUtil {

    val errorResponse = ErrorResponse(
        message = "message",
        code = 1
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