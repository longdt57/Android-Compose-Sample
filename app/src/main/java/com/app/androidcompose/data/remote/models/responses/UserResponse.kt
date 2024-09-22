package com.app.androidcompose.data.remote.models.responses

import androidx.annotation.Keep
import com.app.androidcompose.data.model.User
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class UserResponse(
    val users: List<User>
)
