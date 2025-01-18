package leegroup.module.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import leegroup.module.data.models.GitUser

@Dao
interface GitUserDao {
    @Upsert
    suspend fun upsert(users: List<GitUser>)

    @Query("SELECT * FROM GitUser WHERE id > :since ORDER BY id LIMIT :perPage")
    suspend fun getUsers(since: Long, perPage: Int): List<GitUser>
}