package com.app.androidcompose.domain.exceptions

import androidx.annotation.Keep
import com.app.androidcompose.data.remote.models.responses.ErrorResponse

object NoConnectivityException : RuntimeException()
object ServerException : RuntimeException()

@Keep
data class ApiException(
    val error: ErrorResponse?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
