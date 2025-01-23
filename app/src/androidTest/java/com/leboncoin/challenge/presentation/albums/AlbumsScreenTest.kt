package com.leboncoin.challenge.presentation.albums

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leboncoin.challenge.R
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.domain.model.Album
import com.leboncoin.challenge.presentation.UiText
import com.leboncoin.challenge.util.TestTags
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun title_whenStarting_thenShowTitle() {
        setupAlbumsScreen()

        // Show title text
        val expectedString = context.getString(R.string.albums_text)
        composeRule.onNodeWithText(expectedString).assertIsDisplayed()
    }

    @Test
    fun albumsPaging_whenRefreshLoadStateLoading_thenShowLoadingContent() {
        setupAlbumsScreen()

        // Show loading content
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LOADING_CONTENT).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL).assertIsDisplayed()
    }

    @Test
    fun albumsPaging_whenRefreshLoadStateNotLoading_withAlbums_thenShowAlbums() {

        setupAlbumsScreen(
            refreshLoadState = LoadState.NotLoading(false),
            albums = albumsStub()
        )

        // Show albums screen list
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LIST).assertIsDisplayed()

        // Doesn't show loading content
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LOADING_CONTENT).assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL).assertIsNotDisplayed()
    }

    @Test
    fun albumsPaging_whenRefreshLoadStateNotLoading_withoutAlbums_thenShowEmptyAlbumsText() {
        val sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.Loading,
            append = LoadState.Loading,
        )

        setupAlbumsScreen(refreshLoadState = LoadState.NotLoading(false))

        // Show empty list text
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LIST_EMPTY_TEXT).assertIsDisplayed()

        // Doesn't show screen list
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LIST).assertIsNotDisplayed()

        // Doesn't show loading content
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LOADING_CONTENT).assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL).assertIsNotDisplayed()
    }

    @Test
    fun fetchList_whenError_thenShowDialogError() {
        val dialogTitle = context.getString(R.string.albums_list_dialog_error_title)
        val error = "Something went wrong"

        setupAlbumsScreen(
            refreshLoadState = LoadState.NotLoading(false),
            errorMessage = error
        )

        // Show dialog
        composeRule.onNodeWithText(dialogTitle).assertIsDisplayed()
        composeRule.onNodeWithText(error).assertIsDisplayed()
        composeRule.onNodeWithText(context.getString(R.string.albums_list_dialog_error_retry_button))
            .assertIsDisplayed()
    }

    @Test
    fun clickOnDialogRetryButton() {
        val error = "Something went wrong"

        setupAlbumsScreen(
            refreshLoadState = LoadState.NotLoading(false),
            errorMessage = error
        )

        composeRule.onNodeWithText(context.getString(R.string.albums_list_dialog_error_retry_button))
            .assertIsDisplayed()
            .performClick()
    }

    private fun setupAlbumsScreen(
        albums: List<Album> = emptyList(),
        refreshLoadState: LoadState = LoadState.Loading,
        errorMessage: String? = null
    ) = runTest {

        val paging = PagingData.from(
            data = albums,
            sourceLoadStates = LoadStates(
                refresh = refreshLoadState,
                prepend = LoadState.Loading,
                append = LoadState.Loading,
            )
        )

        composeRule.setContent {
            val lazyAlbumsPaging = flowOf(paging).collectAsLazyPagingItems()
            AlbumsScreen(
                albumsPaging = lazyAlbumsPaging,
                onEventAction = { },
                errorUiText = errorMessage?.let { UiText.DynamicString(it) }
            )
        }
    }

}