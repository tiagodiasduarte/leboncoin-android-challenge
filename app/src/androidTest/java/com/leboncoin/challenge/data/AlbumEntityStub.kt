package com.leboncoin.challenge.data

import com.leboncoin.challenge.data.network.model.NetworkAlbum
import com.leboncoin.challenge.mapper.toEntity
import com.leboncoin.challenge.util.readFromJSONToModel

const val GET_ALBUMS_SUCCESS_RESPONSE = "get_albums_success_response.json"

fun albumsEntityStub() =
    readFromJSONToModel<List<NetworkAlbum>>(GET_ALBUMS_SUCCESS_RESPONSE).map { it.toEntity() }

fun albumEntityStub() = albumsEntityStub().first()
