@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.leboncoin.challenge.presentation.albums

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leboncoin.challenge.R
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.ui.theme.DevicePreviews

@Composable
fun AlbumsRoute(
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AlbumsScreen(uiState)
}

@Composable
fun AlbumsScreen(uiState: AlbumsUiState) {
    Scaffold(
        topBar = { AlbumsTopAppBar() }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (uiState) {
                is AlbumsUiState.Error -> {
                }
                is AlbumsUiState.Loading -> {
                }
                is AlbumsUiState.Success -> {
                    AlbumListScreen(albums = uiState.albums.take(20))
                }
            }
        }
    }
}

@Composable
fun AlbumListScreen(albums: List<Album>) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
        items(albums) { album ->
            AlbumItem(album = album)
        }
    }
}

@Composable
fun AlbumsTopAppBar() {
    TopAppBar(title = { Text(text = stringResource(id = R.string.albums_text)) })
}

@DevicePreviews
@Composable
fun AlbumsScreenPreview() {
    val albums = listOf(
        Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt\"",
            url = "https://via.placeholder.com/600/92c952\"",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        ),
        Album(
            albumId = 1,
            id = 2,
            title = "reprehenderit est deserunt velit ipsam",
            url = "https://via.placeholder.com/600/771796",
            thumbnailUrl = "https://via.placeholder.com/150/771796"
        ),
        Album(
            albumId = 1,
            id = 1,
            title = "officia porro iure quia iusto qui ipsa ut modi",
            url = "https://via.placeholder.com/600/24f355",
            thumbnailUrl = "https://via.placeholder.com/150/24f355"
        )
    )

    AlbumsScreen(AlbumsUiState.Success(albums = albums))
}

@DevicePreviews
@Composable
fun AlbumsScreenErrorPreview() {
    AlbumsScreen(AlbumsUiState.Error("Something went wrong"))
}

