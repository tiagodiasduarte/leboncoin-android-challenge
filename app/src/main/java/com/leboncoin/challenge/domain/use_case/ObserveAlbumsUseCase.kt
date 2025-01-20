@file:OptIn(ExperimentalCoroutinesApi::class)

package com.leboncoin.challenge.domain.use_case

import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObserveAlbumsUseCase(private val repository: AlbumRepository) {

    operator fun invoke(): Flow<Result<List<Album>>> {
        return try {
            repository.getAlbums()
                .distinctUntilChanged()
                .flatMapLatest { albums ->
                    flowOf(Result.Success(albums))
                }

        } catch (e: Exception) {
            flowOf(Result.Error(ErrorEntity.Unknown))
        }
    }

}