package com.leboncoin.challenge.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.leboncoin.challenge.core.Result.Success
import com.leboncoin.challenge.domain.use_case.FetchAlbumsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@HiltWorker
class FetchAlbumsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val getAlbumsUseCase: FetchAlbumsUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {

            val sync = async { getAlbumsUseCase() }

            val syncedSuccessfully = sync.await()

            if (syncedSuccessfully is Success) {
                Result.success()
            } else {
                Result.failure()
            }
        }
    }

}
