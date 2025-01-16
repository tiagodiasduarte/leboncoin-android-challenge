package com.leboncoin.challenge.data.network.model

data class AlbumResponse(
    val albums: List<Album>
){
    data class Album(
        val albumId: Int,
        val id: Int,
        val title: String,
        val url: String,
        val thumbnailUrl: String,
    )
}