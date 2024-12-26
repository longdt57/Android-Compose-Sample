package leegroup.module.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import leegroup.module.data.local.room.models.UserEntity

@Dao()
interface UserDao {

    @Query("SELECT * FROM userentity")
    fun getAllAsFlow(): Flow<List<UserEntity>>

    @Upsert
    suspend fun upsert(users: List<UserEntity>)

    @Query("DELETE FROM userentity")
    suspend fun clearTable()
}