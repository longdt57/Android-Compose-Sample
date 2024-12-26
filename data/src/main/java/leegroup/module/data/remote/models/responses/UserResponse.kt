package leegroup.module.data.remote.models.responses

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import leegroup.module.data.local.room.models.UserEntity
import leegroup.module.domain.models.UserModel

@Serializable
@Keep
data class UserResponse(
    val users: List<User>
)

@Serializable
@Keep
data class User(
    @SerialName("id")
    val id: Int,

    @SerialName("firstName")
    val firstName: String?,

    @SerialName("lastName")
    val lastName: String?
)

fun User.mapToDomain() = UserModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
)

fun User.mapToEntity() = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
)
