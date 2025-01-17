package com.leboncoin.challenge.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "albums"
)
data class AlbumEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)
