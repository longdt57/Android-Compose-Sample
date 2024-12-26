package leegroup.module.data.local.room.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import leegroup.module.domain.models.UserModel

@Entity
@Keep
data class UserEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,
)

fun UserEntity.mapToDomain() = UserModel(
    id = id,
    firstName = firstName,
    lastName = lastName,
)
