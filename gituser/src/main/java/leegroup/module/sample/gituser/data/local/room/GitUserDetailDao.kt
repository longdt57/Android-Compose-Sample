package leegroup.module.sample.gituser.data.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import leegroup.module.sample.gituser.data.models.GitUserDetail

@Dao
internal interface GitUserDetailDao {

    @Query("SELECT * FROM gituserdetail WHERE login = :login")
    suspend fun getUserDetail(login: String): GitUserDetail?

    @Upsert
    suspend fun upsert(user: GitUserDetail)
}
