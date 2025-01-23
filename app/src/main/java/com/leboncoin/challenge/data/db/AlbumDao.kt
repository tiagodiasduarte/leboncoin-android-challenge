package com.leboncoin.challenge.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leboncoin.challenge.data.db.model.AlbumEntity


@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>): List<Long>

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun getPagedAlbums(): PagingSource<Int, AlbumEntity>

}