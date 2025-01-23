package com.leboncoin.challenge.domain.use_case

import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchAlbumsUseCase(private val repository: AlbumRepository) {

    operator fun invoke(): Flow<Result<List<Album>>> = flow {
        try {
            emit(repository.fetchAlbums())

        } catch (e: Exception) {
            emit(Result.Error(ErrorEntity.Unknown))
        }
    }

}