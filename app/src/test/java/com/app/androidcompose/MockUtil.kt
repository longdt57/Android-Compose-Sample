package com.app.androidcompose

import com.app.androidcompose.ui.base.ErrorState
import leegroup.module.domain.exceptions.ApiException
import leegroup.module.domain.models.Error

object MockUtil {
    private const val API_ERROR_MESSAGE = "Something went wrong"

    val apiErrorState = ErrorState.Api(customMessage = API_ERROR_MESSAGE)

    val apiError = ApiException(
        error = Error(message = API_ERROR_MESSAGE),
        httpCode = 404,
        httpMessage = "Not Found"
    )
}