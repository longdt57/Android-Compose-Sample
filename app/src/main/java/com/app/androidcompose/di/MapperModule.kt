package com.app.androidcompose.di

import com.app.androidcompose.ui.mapper.GitUserDetailUiMapper
import com.app.androidcompose.ui.mapper.GitUserDetailUiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface MapperModule {
    @Binds
    fun bindGitUserDetailUiMapper(mapper: GitUserDetailUiMapperImpl): GitUserDetailUiMapper
}