@file:OptIn(ExperimentalCoroutinesApi::class)

package com.leboncoin.challenge.presentation.albums

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leboncoin.challenge.presentation.UiText
import com.leboncoin.challenge.presentation.asUiText
import com.leboncoin.challenge.core.Result
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.domain.use_case.FetchAlbumsUseCase
import com.leboncoin.challenge.domain.use_case.ObserveAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val ALBUMS_VIEW_MODEL_TAG = "AlbumsViewModel"

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val observeAlbumsUseCase: ObserveAlbumsUseCase,
    private val fetchAlbumsUseCase: FetchAlbumsUseCase,
) : ViewModel() {

    private val _errorUiText = MutableStateFlow<UiText?>(null)
    val errorUiText = _errorUiText.asStateFlow()

    private val _albumsState = MutableStateFlow<PagingData<Album>>(PagingData.empty())
    val albumsState = _albumsState.asStateFlow()

    init {
        getAlbums()
        fetchAlbums()
    }

    fun onEvent(event: AlbumsEvent) {
        when (event) {
            AlbumsEvent.Retry -> {
                fetchAlbums()
            }

            AlbumsEvent.CloseDialog -> {
                _errorUiText.value = null
            }
        }
    }

    private fun getAlbums() {
        viewModelScope.launch {
            observeAlbumsUseCase(PAGE_SIZE).mapLatest { result ->
                when (result) {
                    is Result.Error -> {
                        PagingData.empty()
                    }
                    is Result.Success -> {
                        result.data
                    }
                }
            }
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _albumsState.value = it
                }
        }
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            fetchAlbumsUseCase.invoke()
                .collect { result ->
                    when (result) {
                        is Result.Error -> {
                            _errorUiText.value = result.error.asUiText()
                        }

                        is Result.Success -> {
                            Log.d(ALBUMS_VIEW_MODEL_TAG,"Albums loaded successfully!")
                        }
                    }
                }
        }
    }

}