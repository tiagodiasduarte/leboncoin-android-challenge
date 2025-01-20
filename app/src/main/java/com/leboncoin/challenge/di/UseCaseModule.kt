package com.leboncoin.challenge.di

import com.leboncoin.challenge.domain.repository.AlbumRepository
import com.leboncoin.challenge.domain.use_case.FetchAlbumsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesFetchAlbumsUseCases(
        albumRepository: AlbumRepository,
    ): FetchAlbumsUseCase {
        return FetchAlbumsUseCase(albumRepository)
    }

}