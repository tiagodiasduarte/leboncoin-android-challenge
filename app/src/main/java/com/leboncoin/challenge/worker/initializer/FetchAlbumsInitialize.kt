package com.leboncoin.challenge.worker.initializer

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.leboncoin.challenge.worker.FetchAlbumsWorker

private const val FETCH_ALBUMS_WORK = "FetchAlbumsWork"

object FetchAlbums {

    fun initialize(
        workManager: WorkManager
    ) {
        val syncRequest = OneTimeWorkRequestBuilder<FetchAlbumsWorker>()
            .build()

        workManager.enqueueUniqueWork(
            FETCH_ALBUMS_WORK,
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            syncRequest,
        )
    }
}