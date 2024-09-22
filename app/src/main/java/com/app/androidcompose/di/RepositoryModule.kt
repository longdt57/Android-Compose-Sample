package com.app.androidcompose.di

import com.app.androidcompose.data.repository.UserRepositoryImpl
import com.app.androidcompose.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}