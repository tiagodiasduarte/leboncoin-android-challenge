package com.leboncoin.challenge.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leboncoin.challenge.data.db.model.AlbumEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>): List<Long>

    @Query("SELECT * FROM albums ORDER BY id ASC")
    fun getAlbums(): Flow<List<AlbumEntity>>

}