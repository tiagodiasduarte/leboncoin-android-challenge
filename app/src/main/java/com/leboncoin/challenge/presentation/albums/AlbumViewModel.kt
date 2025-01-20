package com.leboncoin.challenge.presentation.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leboncoin.challenge.domain.use_case.ObserveAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.error.ErrorEntity

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    observeAlbumsUseCase: ObserveAlbumsUseCase,
) : ViewModel() {

    val uiState = observeAlbumsUseCase().mapLatest { result ->

        when (result) {
            is Result.Success -> {
                AlbumsUiState.Success(
                    albums = result.data
                )
            }
            is Result.Error -> {
                AlbumsUiState.Error(ErrorEntity.Unknown.toString())
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AlbumsUiState.Loading
    )

}