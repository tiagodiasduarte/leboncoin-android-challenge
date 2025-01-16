package com.leboncoin.challenge.data.repository

import com.leboncoin.challenge.data.network.AlbumService
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.repository.AlbumRepository
import com.leboncoin.challenge.mapper.toAlbum

class AlbumRepositoryImpl(private val albumService: AlbumService) : AlbumRepository {

    override suspend fun getAlbums(): Result<List<Album>> {
        return try {
            Result.Success(albumService.geAlbums().albums.map { it.toAlbum() })
        } catch (exception: Exception) {
            Result.Error(exception.toString())
        }
    }

}