package com.leboncoin.challenge.domain.use_case

import androidx.paging.PagingData
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

const val pageSizeError: String = "The user id can't be equals or less than zero."

class ObserveAlbumsUseCase(private val repository: AlbumRepository) {

    operator fun invoke(pageSize: Int): Flow<Result<PagingData<Album>>> {

        return try {
            if (pageSize <= 0) {
                throw IllegalArgumentException(pageSizeError)
            }

            return repository.getAlbums(pageSize).map { Result.Success(it) }

        } catch (e: IllegalArgumentException) {
            flowOf(Result.Error(ErrorEntity.Unknown))

        } catch (e: Exception) {
            flowOf(Result.Error(ErrorEntity.Unknown))
        }
    }

}
