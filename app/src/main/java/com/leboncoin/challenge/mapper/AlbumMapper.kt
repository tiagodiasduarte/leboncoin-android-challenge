package com.leboncoin.challenge.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.leboncoin.challenge.data.db.model.AlbumEntity
import com.leboncoin.challenge.data.network.model.NetworkAlbum
import com.leboncoin.challenge.domain.model.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun NetworkAlbum.toAlbum(): Album =
    Album(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )

fun NetworkAlbum.toEntity(): AlbumEntity =
    AlbumEntity(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )

fun Album.toEntity(): AlbumEntity =
    AlbumEntity(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )

fun AlbumEntity.toAlbum(): Album =
    Album(
        albumId = this.albumId,
        id = this.id,
        title = this.title,
        url = this.url,
        thumbnailUrl = this.thumbnailUrl
    )

fun Flow<List<AlbumEntity>>.toAlbums(): Flow<List<Album>> =
    this.map { albumEntities -> albumEntities.map { it.toAlbum() } }

@JvmName("FlowPagingDataAlbumEntityToPagingDataAlbum")
fun Flow<PagingData<AlbumEntity>>.toAlbums(): Flow<PagingData<Album>> =
    this.map { albumEntities -> albumEntities.map { it.toAlbum() } }

@JvmName("PagingDataAlbumEntityToPagingDataAlbum")
fun PagingData<AlbumEntity>.toAlbums(): PagingData<Album> =
    this.map { albumEntity -> albumEntity.toAlbum()}

