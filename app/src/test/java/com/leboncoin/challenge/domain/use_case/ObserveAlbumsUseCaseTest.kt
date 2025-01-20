package com.leboncoin.challenge.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ObserveAlbumsUseCaseTest {

    private lateinit var subject: ObserveAlbumsUseCase
    private val albumRepository = mockk<AlbumRepository>()

    @Before
    fun setUp() {
        subject = ObserveAlbumsUseCase(
            repository = albumRepository
        )
    }

    @Test
    fun success_returnNotes() = runTest {
        val expectedResult = albumsStub()
        coEvery { albumRepository.getAlbums() } returns flowOf(expectedResult)

        val result = subject().first() as Result.Success
        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(result.data).isEqualTo(expectedResult)

        coVerify { albumRepository.getAlbums() }
    }

}
