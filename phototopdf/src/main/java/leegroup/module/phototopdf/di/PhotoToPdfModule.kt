package leegroup.module.phototopdf.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import leegroup.module.coreimage.file.ExternalFileSource

@Module
@InstallIn(SingletonComponent::class)
internal class PhotoToPdfModule {

    @Provides
    fun provideExternalFileSource(@ApplicationContext context: Context): ExternalFileSource =
        ExternalFileSource(context)
}
