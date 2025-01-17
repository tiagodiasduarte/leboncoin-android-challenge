package com.leboncoin.challenge.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.data.albumsEntityStub
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.data.db.AlbumDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    private lateinit var database: AlbumDatabase
    private lateinit var dao: AlbumDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AlbumDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.albumDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAlbums() = runTest {
        val expectedResult = albumsEntityStub()
        dao.insertAlbums(expectedResult)

        val allAlbums = dao.getAlbums().first()
        assertThat(allAlbums).isEqualTo(expectedResult)
    }

    @Test
    fun getAlbums() = runTest {
        val expectedResult = albumsEntityStub()
        dao.insertAlbums(expectedResult)

        val allAlbums = dao.getAlbums().first()
        assertThat(allAlbums).hasSize(expectedResult.size)
    }

}