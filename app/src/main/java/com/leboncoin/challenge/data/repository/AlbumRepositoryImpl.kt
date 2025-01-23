@file:OptIn(ExperimentalCoroutinesApi::class)

package com.leboncoin.challenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.data.network.AlbumService
import com.leboncoin.challenge.data.network.extensions.toErrorEntity
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository
import com.leboncoin.challenge.mapper.toAlbum
import com.leboncoin.challenge.mapper.toAlbums
import com.leboncoin.challenge.mapper.toEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class AlbumRepositoryImpl(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) : AlbumRepository {

    override suspend fun fetchAlbums(): Result<List<Album>> {
        return try {
            val networkAlbums = albumService.geAlbums().map { it.toAlbum() }

            Result.Success(networkAlbums)

        } catch (exception: Exception) {
            Result.Error(exception.toErrorEntity())
        }
    }

    override fun getAlbums(pageSize: Int): Flow<PagingData<Album>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { albumDao.getPagedAlbums() }
        ).flow.mapLatest { albumEntities ->
            albumEntities.toAlbums()
        }
    }

    override suspend fun saveAlbums(albums: List<Album>): List<Long> {
        return albumDao.insertAlbums(albums.map { it.toEntity() })
    }

}