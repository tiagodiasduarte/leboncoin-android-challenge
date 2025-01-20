package com.leboncoin.challenge.data.repository

import com.leboncoin.challenge.data.network.AlbumService
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.domain.repository.AlbumRepository
import com.leboncoin.challenge.mapper.toAlbum
import com.leboncoin.challenge.mapper.toAlbums
import com.leboncoin.challenge.mapper.toEntity
import kotlinx.coroutines.flow.Flow

class AlbumRepositoryImpl(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) : AlbumRepository {

    override suspend fun fetchAlbums(): Result<List<Album>> {
        return try {
            Result.Success(albumService.geAlbums().map { it.toAlbum() })
        } catch (exception: Exception) {
            Result.Error(exception.toErrorEntity())
        }
    }

    override suspend fun getAlbums(): Flow<List<Album>> {
        return albumDao.getAlbums().toAlbums()
    }

    override suspend fun insertAlbums(albums: List<Album>): List<Long> {
       return albumDao.insertAlbums(albums.map { it.toEntity() })
    }

}