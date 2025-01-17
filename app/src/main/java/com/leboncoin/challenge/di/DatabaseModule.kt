package com.leboncoin.challenge.di

import android.content.Context
import androidx.room.Room
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.data.db.AlbumDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAlbumDao(albumDatabase: AlbumDatabase): AlbumDao {
        return albumDatabase.albumDao()
    }

    @Provides
    @Singleton
    fun providesAlbumDatabase(@ApplicationContext context: Context): AlbumDatabase {
        return Room.databaseBuilder(
            context,
            AlbumDatabase::class.java,
            AlbumDatabase.DATABASE_NAME
        )
            .build()
    }

}