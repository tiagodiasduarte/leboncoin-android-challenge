package com.leboncoin.challenge.data.network.extensions

import com.leboncoin.challenge.domain.error.ErrorEntity
import retrofit2.HttpException
import java.net.HttpURLConnection

fun Throwable.toErrorEntity(): ErrorEntity = when (this) {
    is HttpException -> parseHttpError()
    else -> ErrorEntity.Unknown
}

fun HttpException.parseHttpError(): ErrorEntity = when (code()) {
    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.Network.ACCESS_DENIED
    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.Network.NOT_FOUND
    HttpURLConnection.HTTP_CLIENT_TIMEOUT -> ErrorEntity.Network.TIMEOUT
    HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorEntity.Network.INTERNAL_SERVER_ERROR
    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.Network.SERVER_UNAVAILABLE
    else -> ErrorEntity.Network.UNKNOWN
}