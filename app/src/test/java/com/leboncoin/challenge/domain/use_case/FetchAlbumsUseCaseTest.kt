package com.leboncoin.challenge.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchAlbumsUseCaseTest {

    private lateinit var subject: FetchAlbumsUseCase
    private val albumRepository = mockk<AlbumRepository>()

    @Before
    fun setUp() {
        subject = FetchAlbumsUseCase(
            repository = albumRepository
        )
    }

    @Test
    fun success_returnAlbums() = runTest {
        val expectedResult = albumsStub()
        coEvery { albumRepository.fetchAlbums() } returns Result.Success(
            expectedResult
        )

        val result = subject() as Result.Success
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isEqualTo(expectedResult)

        coVerify { albumRepository.fetchAlbums() }
    }

}
