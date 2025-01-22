package com.leboncoin.challenge.presentation.albums

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leboncoin.challenge.R
import com.leboncoin.challenge.data.albumsStub
import com.leboncoin.challenge.util.TestTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun title_starting_isShown() {
        setupAlbumsScreen(AlbumsUiState.Loading)

        // Show title text
        val expectedString = context.getString(R.string.albums_text)
        composeRule.onNodeWithText(expectedString).assertIsDisplayed()
    }

    @Test
    fun uiStateLoading_showLoadingContent() {
        setupAlbumsScreen(AlbumsUiState.Loading)

        // Show loading content
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LOADING_CONTENT).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL).assertIsDisplayed()
    }

    @Test
    fun uiStateSuccess_showAlbums() {
        setupAlbumsScreen()

        // Show albums screen list
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LIST).assertIsDisplayed()

        // Doesn't show loading content
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_LOADING_CONTENT).assertIsNotDisplayed()
        composeRule.onNodeWithTag(TestTags.ALBUMS_SCREEN_PROGRESS_WHEEL).assertIsNotDisplayed()
    }

    private fun setupAlbumsScreen(
        uiState: AlbumsUiState = AlbumsUiState.Success(albumsStub())
    ) {
        composeRule.setContent {
            AlbumsScreen(uiState)
        }
    }

}