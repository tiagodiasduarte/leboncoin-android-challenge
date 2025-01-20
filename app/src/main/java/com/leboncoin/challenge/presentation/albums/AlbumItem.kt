package com.leboncoin.challenge.presentation.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.leboncoin.challenge.R
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.ui.theme.DevicePreviews

@Composable
fun AlbumItem(album: Album) {
    Column(
        modifier = Modifier
            .width(150.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        AsyncImage(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .align(Alignment.CenterHorizontally),
            model = album.url,
            error = painterResource(id = R.drawable.ic_album_placeholder),
            contentDescription = stringResource(id = R.string.albums_image_content_description),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .align(Alignment.CenterHorizontally),
            text = album.title,
            maxLines = 3
        )
    }
}

@DevicePreviews
@Composable
fun AlbumItemPreview() {
    AlbumItem(
        Album(
            albumId = 1,
            id = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952",
        )
    )
}