package com.leboncoin.challenge.domain.repository

import com.leboncoin.challenge.domain.model.Album
import kotlinx.coroutines.flow.Flow
import com.leboncoin.challenge.core.Result

interface AlbumRepository {

    suspend fun fetchAlbums(): Result<List<Album>>

    fun getAlbums(): Flow<List<Album>>

    suspend fun insertAlbums(albums: List<Album>): List<Long>

}