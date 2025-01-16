package com.leboncoin.challenge.di

import com.leboncoin.challenge.data.network.AlbumService
import com.leboncoin.challenge.data.repository.AlbumRepositoryImpl
import com.leboncoin.challenge.domain.repository.AlbumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesAlbumRepository(albumService: AlbumService): AlbumRepository {
        return AlbumRepositoryImpl(
            albumService = albumService
        )
    }

}