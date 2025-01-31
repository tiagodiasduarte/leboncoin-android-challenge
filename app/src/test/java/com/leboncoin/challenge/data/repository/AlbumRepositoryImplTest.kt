package com.leboncoin.challenge.data.repository

import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.GET_ALBUMS_ERROR_RESPONSE
import com.leboncoin.challenge.data.GET_ALBUMS_SUCCESS_RESPONSE
import com.leboncoin.challenge.data.db.AlbumDao
import com.leboncoin.challenge.data.network.model.NetworkAlbum
import com.leboncoin.challenge.mapper.toAlbum
import com.leboncoin.challenge.rules.RemoteTestRule
import com.leboncoin.challenge.rules.toServerErrorResponse
import com.leboncoin.challenge.rules.toServerSuccessResponse
import com.leboncoin.challenge.util.readFromJSONToModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumRepositoryImplTest {

    @get:Rule
    val remoteRule = RemoteTestRule()

    private lateinit var subject: AlbumRepositoryImpl
    private val albumDao = mockk<AlbumDao>()

    @Before
    fun setUp() {
        subject = AlbumRepositoryImpl(
            albumService = remoteRule.createTestService(),
            albumDao = albumDao
        )
    }

    @Test
    fun fetchAlbums_whenSuccess_thenReturnSuccess() = runTest {
        val expectedResult =
            readFromJSONToModel<List<NetworkAlbum>>(GET_ALBUMS_SUCCESS_RESPONSE).map { it.toAlbum() }
        remoteRule.toServerSuccessResponse(GET_ALBUMS_SUCCESS_RESPONSE)

        val result = subject.fetchAlbums() as Result.Success

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isEqualTo(expectedResult)
    }

    @Test
    fun fetchAlbums_whenError_ThenReturnError() = runTest {
        remoteRule.toServerErrorResponse(GET_ALBUMS_ERROR_RESPONSE)

        val result = subject.fetchAlbums() as Result.Error
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun saveAlbums_whenSuccess_thenReturnSuccess() = runTest {
        val expectedResult =
            readFromJSONToModel<List<NetworkAlbum>>(GET_ALBUMS_SUCCESS_RESPONSE).map { it.toAlbum() }
        remoteRule.toServerSuccessResponse(GET_ALBUMS_SUCCESS_RESPONSE)

        val ids = expectedResult.map { it.id.toLong() }
        coEvery { albumDao.insertAlbums(any()) } returns ids

        val result = subject.saveAlbums(expectedResult)
        assertThat(result).isEqualTo(ids)

        coVerify { albumDao.insertAlbums(any()) }
    }

}