package com.leboncoin.challenge.domain.repository

import androidx.paging.PagingData
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun fetchAlbums(): Result<List<Album>>

    fun getAlbums(pageSize: Int): Flow<PagingData<Album>>

    suspend fun insertAlbums(albums: List<Album>): List<Long>

}