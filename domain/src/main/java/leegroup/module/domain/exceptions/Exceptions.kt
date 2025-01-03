package leegroup.module.domain.exceptions

import leegroup.module.domain.models.Error

object NoConnectivityException : RuntimeException()
object ServerException : RuntimeException()

data class ApiException(
    val error: Error?,
    val httpCode: Int,
    val httpMessage: String?
) : RuntimeException()
