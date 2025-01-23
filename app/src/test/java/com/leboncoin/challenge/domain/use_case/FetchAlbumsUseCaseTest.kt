package com.leboncoin.challenge.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchAlbumsUseCaseTest {

    private lateinit var subject: FetchAlbumsUseCase
    private val albumRepository = mockk<AlbumRepository>()

    @Test
    fun success_returnAlbums() = runTest {
        val expectedResult = albumsStub()
        coEvery { albumRepository.fetchAlbums() } returns Result.Success(
            expectedResult
        )

        subject = FetchAlbumsUseCase(albumRepository)

        val result = subject().last() as Result.Success
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isEqualTo(expectedResult)

        coVerify { albumRepository.fetchAlbums() }
    }

}
