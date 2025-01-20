package com.leboncoin.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.WorkManager
import com.leboncoin.challenge.ui.theme.ChallengeTheme
import com.leboncoin.challenge.worker.initializer.FetchAlbums
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ChallengeTheme {
            }
        }

        fetchAlbums()
    }

    private fun fetchAlbums() {
        FetchAlbums.initialize(
            workManager = WorkManager.getInstance(applicationContext)
        )
    }

}

