package leegroup.module.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import leegroup.module.data.models.GitUser

@Dao
interface GitUserDao {
    @Upsert
    suspend fun upsert(users: List<GitUser>)

    @Query("SELECT * FROM GitUser ORDER BY id LIMIT :perPage OFFSET :since")
    suspend fun getUsers(since: Int, perPage: Int): List<GitUser>
}