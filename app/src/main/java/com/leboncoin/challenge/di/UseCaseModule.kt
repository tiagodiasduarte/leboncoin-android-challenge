package com.leboncoin.challenge.di

import com.leboncoin.challenge.domain.repository.AlbumRepository
import com.leboncoin.challenge.domain.use_case.FetchAlbumsUseCase
import com.leboncoin.challenge.domain.use_case.ObserveAlbumsUseCase
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

    @Provides
    @Singleton
    fun providesObserveAlbumsUseCases(
        albumRepository: AlbumRepository,
    ): ObserveAlbumsUseCase {
        return ObserveAlbumsUseCase(albumRepository)
    }

}