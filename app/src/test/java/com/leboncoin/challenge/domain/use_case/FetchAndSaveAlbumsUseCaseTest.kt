package com.leboncoin.challenge.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchAndSaveAlbumsUseCaseTest {

    private lateinit var subject: FetchAndSaveAlbumsUseCase
    private val albumRepository = mockk<AlbumRepository>()

    @Test
    fun whenSuccess_thenReturnAlbums() = runTest {
        val expectedResult = albumsStub()
        coEvery { albumRepository.fetchAlbums() } returns Result.Success(
            expectedResult
        )

        val ids = expectedResult.map { it.id.toLong() }
        coEvery { albumRepository.saveAlbums(any()) } returns ids

        subject = FetchAndSaveAlbumsUseCase(albumRepository)

        val result = subject().last()  as Result.Success
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isEqualTo(expectedResult)

        coVerify { albumRepository.fetchAlbums() }
        coVerify { albumRepository.saveAlbums(any()) }
    }

    @Test
    fun whenError_thenReturnError() = runTest {
        coEvery { albumRepository.fetchAlbums() } returns Result.Error(
            ErrorEntity.Unknown
        )

        subject = FetchAndSaveAlbumsUseCase(albumRepository)

        val result = subject().last()  as Result.Error
        assertThat(result.error).isEqualTo(ErrorEntity.Unknown)

        coVerify { albumRepository.fetchAlbums() }
    }

}
