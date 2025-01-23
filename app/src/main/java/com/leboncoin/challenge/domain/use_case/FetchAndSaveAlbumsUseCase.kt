package com.leboncoin.challenge.domain.use_case

import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FetchAndSaveAlbumsUseCase(private val repository: AlbumRepository) {

    operator fun invoke(): Flow<Result<List<Album>>> = flow {
        try {
            when (val result = repository.fetchAlbums()) {
                is Result.Error -> {
                    emit(Result.Error(ErrorEntity.Unknown))
                }
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        repository.saveAlbums(result.data)
                    }

                    emit(Result.Success(result.data))
                }
            }

        } catch (e: Exception) {
            emit(Result.Error(ErrorEntity.Unknown))
        }
    }

}