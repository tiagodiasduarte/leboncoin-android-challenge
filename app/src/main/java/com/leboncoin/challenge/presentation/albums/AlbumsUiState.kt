package com.leboncoin.challenge.presentation.albums

import androidx.paging.PagingData
import com.leboncoin.challenge.domain.model.Album

sealed interface AlbumsUiState {
    data object Loading : AlbumsUiState

    data class Success(
        val albums: PagingData<Album>
    ) : AlbumsUiState

    data class Error(val error: String) : AlbumsUiState
}