package com.leboncoin.challenge.presentation.albums

sealed class AlbumsEvent {
    data object Retry : AlbumsEvent()
    data object CloseDialog : AlbumsEvent()
}