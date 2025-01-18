package leegroup.module.sample.gituser.di.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.sample.gituser.data.local.room.AppDatabase
import leegroup.module.sample.gituser.data.local.room.GitUserDao
import leegroup.module.sample.gituser.data.local.room.GitUserDetailDao

private const val APP_DATABASE = "app-database"

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideAppRoom(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, APP_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGitUserDao(database: AppDatabase): GitUserDao = database.gitUserDao()

    @Provides
    fun provideGitUserDetailDao(database: AppDatabase): GitUserDetailDao =
        database.gitUserDetailDao()
}