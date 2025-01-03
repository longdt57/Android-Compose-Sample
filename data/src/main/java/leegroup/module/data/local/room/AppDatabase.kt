package leegroup.module.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import leegroup.module.data.models.GitUser
import leegroup.module.data.models.GitUserDetail

@Database(entities = [GitUser::class, GitUserDetail::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gitUserDao(): GitUserDao
    abstract fun gitUserDetailDao(): GitUserDetailDao
}