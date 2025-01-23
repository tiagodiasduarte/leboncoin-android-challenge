package com.leboncoin.challenge.domain.use_case

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveAlbumsUseCaseTest {

    private lateinit var subject: ObserveAlbumsUseCase
    private val albumRepository = mockk<AlbumRepository>()

    @Test
    fun whenSuccessfully_thenReturnAlbums() = runTest {
        val expectedResult = albumsStub()
        coEvery { albumRepository.getAlbums(any()) } returns flowOf(PagingData.from(expectedResult))

        subject = ObserveAlbumsUseCase(albumRepository)

        val result = subject(20).first()
        assertThat(result).isInstanceOf(Result.Success::class.java)

        coVerify { albumRepository.getAlbums(any()) }
    }

    @Test
    fun whenError_withPageSizeLessEqualsZero_thenReturnError() = runTest {
        subject = ObserveAlbumsUseCase(albumRepository)

        val result = subject(0).first()
        assertThat(result).isInstanceOf(Result.Error::class.java)

        val errorResult = result as Result.Error
        assertThat(errorResult.error as ErrorEntity.Unknown)
            .isInstanceOf(ErrorEntity.Unknown::class.java)
    }

}
