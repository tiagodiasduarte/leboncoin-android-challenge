package com.leboncoin.challenge.domain.use_case

import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository

class FetchAlbumsUseCase(private val repository: AlbumRepository) {

    suspend operator fun invoke(): Result<List<Album>> {
        return repository.fetchAlbums()
    }

}