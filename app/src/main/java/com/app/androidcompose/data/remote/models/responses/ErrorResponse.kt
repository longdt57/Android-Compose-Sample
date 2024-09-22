package com.app.androidcompose.data.remote.models.responses

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ErrorResponse(
    @SerialName("message")
    val message: String,
    @SerialName("error_code")
    val code: Int? = null,
)
