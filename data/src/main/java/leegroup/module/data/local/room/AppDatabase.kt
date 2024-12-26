package leegroup.module.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import leegroup.module.data.local.room.models.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}