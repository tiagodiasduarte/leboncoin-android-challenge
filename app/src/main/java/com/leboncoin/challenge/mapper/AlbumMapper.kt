package com.leboncoin.challenge.mapper

import com.leboncoin.challenge.data.network.model.NetworkAlbum
import com.leboncoin.challenge.domain.model.Album

fun NetworkAlbum.toAlbum(): Album =
    Album(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )
