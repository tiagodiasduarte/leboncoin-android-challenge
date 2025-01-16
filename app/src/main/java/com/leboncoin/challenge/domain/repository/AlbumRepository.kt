package com.leboncoin.challenge.domain.repository

import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.core.Result

interface AlbumRepository {
    suspend fun getAlbums(): Result<List<Album>>
}