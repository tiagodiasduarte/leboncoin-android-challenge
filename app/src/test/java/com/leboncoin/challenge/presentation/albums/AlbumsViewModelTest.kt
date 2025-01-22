package com.leboncoin.challenge.presentation.albums

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.error.ErrorEntity
import com.leboncoin.challenge.domain.use_case.ObserveAlbumsUseCase
import com.leboncoin.challenge.rules.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class AlbumsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AlbumsViewModel
    private val observeAlbumsUseCase = mockk<ObserveAlbumsUseCase>(relaxed = true)

    @Test
    fun uiState_starting_returnLoading() {
        viewModel = AlbumsViewModel(observeAlbumsUseCase)

        assertThat(viewModel.uiState.value).isInstanceOf(AlbumsUiState.Loading::class.java)
    }

    @Test
    fun observeAlbums_success_returnAlbums() = runTest {
        val expectedResult = albumsStub()

        every { observeAlbumsUseCase() } returns flow {
            emit(com.leboncoin.challenge.core.Result.Success(expectedResult))
        }

        viewModel = AlbumsViewModel(observeAlbumsUseCase)

        viewModel.uiState.test {

            awaitItem() as AlbumsUiState.Loading

            val successState = awaitItem() as AlbumsUiState.Success
            assertThat(successState.albums.size).isEqualTo(expectedResult.size)

            cancelAndIgnoreRemainingEvents()
        }

        verify { observeAlbumsUseCase() }
    }

    @Test
    fun observeAlbums_error_returnError() = runTest {
        val error = ErrorEntity.Unknown
        every { observeAlbumsUseCase() } returns flow {
            emit(com.leboncoin.challenge.core.Result.Error(error))
        }

        viewModel = AlbumsViewModel(observeAlbumsUseCase)

        viewModel.uiState.test {
            awaitItem() as AlbumsUiState.Loading

            val errorState = awaitItem() as AlbumsUiState.Error
            assertThat(errorState.error).isEqualTo(error.toString())

            cancelAndIgnoreRemainingEvents()
        }

        verify { observeAlbumsUseCase() }
    }

}