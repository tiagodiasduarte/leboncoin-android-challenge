package com.leboncoin.challenge.presentation.albums

import com.leboncoin.challenge.domain.model.Album

sealed interface AlbumsUiState {
    data object Loading : AlbumsUiState

    data class Success(
        val albums: List<Album> = emptyList()
    ) : AlbumsUiState

    data class Error(val error: String) : AlbumsUiState
}