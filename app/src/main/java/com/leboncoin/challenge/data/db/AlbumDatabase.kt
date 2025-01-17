package com.leboncoin.challenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leboncoin.challenge.data.db.model.AlbumEntity


@Database(
    entities = [AlbumEntity::class],
    version = 1, exportSchema = false
)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        const val DATABASE_NAME = "albums_db"
    }
}