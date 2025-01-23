package com.leboncoin.challenge.presentation.albums

sealed class AlbumsEvent {
    data object FetchAlbums : AlbumsEvent()
    data object CloseDialog : AlbumsEvent()
}