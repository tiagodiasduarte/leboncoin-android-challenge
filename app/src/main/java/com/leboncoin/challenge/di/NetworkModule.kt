package com.leboncoin.challenge.di

import com.leboncoin.challenge.BuildConfig
import com.leboncoin.challenge.data.network.AlbumService
import com.leboncoin.challenge.data.network.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return RetrofitClient(
            baseUrl = BuildConfig.BASE_URL
        ).create()
    }

    @Singleton
    @Provides
    fun providesAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }

}