package com.leboncoin.challenge.util

import androidx.paging.PagingSource
import kotlinx.coroutines.runBlocking

class PagingSourceTestHelper<T : Any>(
    private val pagingSource: PagingSource<Int, T>
) {
    fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, T> {
        return runBlocking {
            pagingSource.load(params)
        }
    }
}