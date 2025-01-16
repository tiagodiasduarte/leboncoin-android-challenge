package com.leboncoin.challenge.data.network

import com.leboncoin.challenge.data.network.model.AlbumResponse
import retrofit2.http.GET

interface AlbumService {

    @GET("img/shared/technical-test.json")
    suspend fun geAlbums(): AlbumResponse

}