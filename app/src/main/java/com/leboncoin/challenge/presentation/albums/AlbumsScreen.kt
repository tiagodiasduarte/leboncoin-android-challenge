@file:OptIn(ExperimentalMaterial3Api::class)

package com.leboncoin.challenge.presentation.albums

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.leboncoin.challenge.R
import com.leboncoin.challenge.presentation.UiText
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.ui.theme.DevicePreviews
import com.leboncoin.challenge.ui.theme.Dimensions
import com.leboncoin.challenge.util.TestTags.ALBUMS_SCREEN_LIST
import com.leboncoin.challenge.util.TestTags.ALBUMS_SCREEN_LIST_EMPTY_TEXT
import com.leboncoin.challenge.util.TestTags.ALBUMS_SCREEN_LOADING_CONTENT
import com.leboncoin.challenge.util.TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AlbumsRoute(
    viewModel: AlbumsViewModel = hiltViewModel()
) {
    val errorUiText by viewModel.errorUiText.collectAsStateWithLifecycle()
    val albumsPaging = viewModel.albumsState.collectAsLazyPagingItems()

    AlbumsScreen(
        albumsPaging = albumsPaging,
        onEventAction = {
            viewModel.onEvent(it)
        },
        errorUiText = errorUiText
    )
}

@Composable
fun AlbumsScreen(
    albumsPaging: LazyPagingItems<Album>,
    onEventAction: (AlbumsEvent) -> Unit,
    errorUiText: UiText? = null
) {

    Scaffold(
        topBar = { AlbumsTopAppBar() },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (albumsPaging.loadState.refresh) {
                is LoadState.Error -> {
                }

                is LoadState.Loading -> {
                    LoadingContent()
                }

                is LoadState.NotLoading -> {
                    if (albumsPaging.itemCount > 0) {
                        AlbumListScreen(albumsPaging)
                    } else {
                        EmptyListContent(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }

        if (errorUiText != null) {
            AlertDialogError(
                message = errorUiText.asString(),
                onDialogClosed = { onEventAction(AlbumsEvent.CloseDialog) },
                onRetryClick = {
                    onEventAction(AlbumsEvent.CloseDialog)
                    onEventAction(AlbumsEvent.FetchAlbums)
                }
            )
        }
    }
}

@Composable
fun AlbumsTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.albums_text),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun AlbumListScreen(albumsPaging: LazyPagingItems<Album>) {
    LazyVerticalGrid(
        modifier = Modifier.testTag(ALBUMS_SCREEN_LIST),
        columns = GridCells.Adaptive(minSize = Dimensions.AlbumItemWidth)
    ) {
        items(albumsPaging.itemCount) { index ->
            AlbumItem(albumsPaging[index]!!)
        }
    }
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier
            .testTag(ALBUMS_SCREEN_LOADING_CONTENT)
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = false) {}
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(ALBUMS_SCREEN_PROGRESS_WHEEL)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun EmptyListContent(modifier: Modifier) {
    Text(
        modifier = modifier.testTag(ALBUMS_SCREEN_LIST_EMPTY_TEXT),
        text = stringResource(id = R.string.albums_list_empty_text)
    )
}

@Composable
fun AlertDialogError(message: String, onDialogClosed: () -> Unit, onRetryClick: () -> Unit) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { onDialogClosed.invoke() },
        title = {
            Text(stringResource(id = R.string.albums_list_dialog_error_title))
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = { onRetryClick.invoke() }
            ) {
                Text(stringResource(id = R.string.albums_list_dialog_error_retry_button))
            }
        },
    )
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

    val albumsPagingStateFlow = MutableStateFlow(PagingData.from(albums))
    AlbumsScreen(
        albumsPaging = albumsPagingStateFlow.collectAsLazyPagingItems(),
        onEventAction = {}
    )
}

@DevicePreviews
@Composable
fun AlbumsScreenLoadingPreview() {
    val albumsPaging = PagingData.from(
        data = emptyList<Album>(),
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )
    )

    AlbumsScreen(
        albumsPaging = MutableStateFlow(albumsPaging).collectAsLazyPagingItems(),
        onEventAction = {}
    )
}

@DevicePreviews
@Composable
fun AlbumsScreenErrorPreview() {
    val albumsPaging = PagingData.from(
        data = emptyList<Album>(),
        sourceLoadStates = LoadStates(
            refresh = LoadState.Error(Throwable("Something went wrong")),
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )
    )

    AlbumsScreen(
        albumsPaging = MutableStateFlow(albumsPaging).collectAsLazyPagingItems(),
        onEventAction = {},
        errorUiText = UiText.DynamicString("Something went wrong")
    )
}
