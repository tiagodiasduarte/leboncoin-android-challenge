package com.leboncoin.challenge.presentation.albums

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.use_case.ObserveAlbumsUseCase
import com.leboncoin.challenge.rules.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AlbumsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var subject: AlbumsViewModel
    private val observeAlbumsUseCase = mockk<ObserveAlbumsUseCase>(relaxed = true)


    @Test
    fun observeAlbums_whenSuccess_thenReturnAlbums() = runTest {
        val expectedResult = albumsStub()

        every { observeAlbumsUseCase(any()) } returns flow {
            emit(Result.Success(PagingData.from(expectedResult)))
        }

        subject = AlbumsViewModel(observeAlbumsUseCase)

        subject.albumsState.test {
            awaitItem()

            val items = flowOf(awaitItem())
            val albumsSnapshot: List<Album> = items.asSnapshot()

            assertThat(albumsSnapshot.size).isEqualTo(expectedResult.size)

            cancelAndIgnoreRemainingEvents()
        }

        verify { observeAlbumsUseCase(any()) }
    }

}