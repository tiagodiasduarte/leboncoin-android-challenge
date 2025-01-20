package com.leboncoin.challenge.domain.error

sealed interface ErrorEntity  {

    enum class Network: ErrorEntity {
        TIMEOUT,
        NOT_FOUND,
        ACCESS_DENIED,
        INTERNAL_SERVER_ERROR,
        SERVER_UNAVAILABLE,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local: ErrorEntity {
        DISK_FULL
    }

    data object Unknown : ErrorEntity
}