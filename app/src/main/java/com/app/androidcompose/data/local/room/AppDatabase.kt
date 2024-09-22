package com.app.androidcompose.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.androidcompose.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}