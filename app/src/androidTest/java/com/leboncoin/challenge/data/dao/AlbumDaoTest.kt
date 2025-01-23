package com.leboncoin.challenge.data.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.data.albumsEntityStub
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.data.db.AlbumDatabase
import com.leboncoin.challenge.util.PagingSourceTestHelper
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

        val albums = dao.getAlbums()
        assertThat(albums).isEqualTo(expectedResult)
        assertThat(albums).hasSize(expectedResult.size)
    }

    @Test
    fun getAlbums() = runTest {
        val expectedResult = albumsEntityStub().take(10)
        dao.insertAlbums(expectedResult)

        val allAlbums = dao.getAlbums()
        assertThat(allAlbums).hasSize(expectedResult.size)
    }

    @Test
    fun getPagedAlbums() = runTest {
        val expectedResult = albumsEntityStub().take(10)

        dao.insertAlbums(expectedResult)

        val pagingSource = dao.getPagedAlbums()

        val actual = PagingSourceTestHelper(pagingSource).load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = expectedResult.size,
                placeholdersEnabled = false
            )
        )

        val page = actual as PagingSource.LoadResult.Page
        assertThat(page.data).isEqualTo(expectedResult)
    }

}