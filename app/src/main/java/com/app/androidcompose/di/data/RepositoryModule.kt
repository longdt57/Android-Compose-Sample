package com.app.androidcompose.di.data

import leegroup.module.data.repositories.UserRepositoryImpl
import leegroup.module.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}