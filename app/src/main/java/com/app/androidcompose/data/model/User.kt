package com.app.androidcompose.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.androidcompose.domain.model.UserModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Keep
data class User(
    @SerialName("id")
    @PrimaryKey val id: Int,

    @SerialName("firstName")
    @ColumnInfo(name = "first_name") val firstName: String?,

    @SerialName("lastName")
    @ColumnInfo(name = "last_name") val lastName: String?
)

fun User.mapToDomain() = this