package com.app.androidcompose.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import leegroup.module.domain.repositories.AppPreferencesRepository
import leegroup.module.domain.repositories.GitUserDetailRepository
import leegroup.module.domain.repositories.GitUserRepository
import leegroup.module.sample.gituser.data.repositories.AppPreferencesRepositoryImpl
import leegroup.module.sample.gituser.data.repositories.GitUserDetailRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindGitUserRepository(repository: leegroup.module.sample.gituser.data.repositories.GitUserRepositoryImpl): GitUserRepository

    @Binds
    fun bindGitUserDetailRepository(repository: GitUserDetailRepositoryImpl): GitUserDetailRepository

    @Binds
    fun bindAppPreferencesRepository(
        appPreferencesRepositoryImpl: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository
}