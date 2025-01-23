package com.leboncoin.challenge.presentation

import com.leboncoin.challenge.R
import com.leboncoin.challenge.domain.error.ErrorEntity

fun ErrorEntity.asUiText(): UiText {

    return when (this) {
        ErrorEntity.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        ErrorEntity.Network.TIMEOUT -> UiText.StringResource(
            R.string.error_request_timed_out
        )

        ErrorEntity.Network.NOT_FOUND -> UiText.StringResource(
            R.string.error_not_found
        )

        ErrorEntity.Network.ACCESS_DENIED -> UiText.StringResource(
            R.string.error_access_denied
        )

        ErrorEntity.Network.INTERNAL_SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )

        ErrorEntity.Network.SERVER_UNAVAILABLE -> UiText.StringResource(
            R.string.error_no_internet
        )

        ErrorEntity.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        ErrorEntity.Network.UNKNOWN -> UiText.StringResource(
            R.string.error_unknown
        )

        is ErrorEntity.Unknown -> UiText.StringResource(R.string.error_unknown)
    }

}