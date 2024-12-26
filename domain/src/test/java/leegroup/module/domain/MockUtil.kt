package leegroup.module.domain

import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.models.Error
import leegroup.module.domain.models.UserModel

object MockUtil {

    val apiException = ApiException(
        error = Error(
            message = "message",
            code = 1,
        ),
        httpCode = 400,
        httpMessage = "Bad Request "
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
}