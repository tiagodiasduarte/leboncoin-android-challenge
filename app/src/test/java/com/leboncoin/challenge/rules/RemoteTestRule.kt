package com.leboncoin.challenge.rules

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.leboncoin.challenge.util.readFromJSONToString
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

@VisibleForTesting(otherwise = Modifier.PRIVATE)
class RemoteTestRule : TestWatcher() {

    private val mockWebServer = MockWebServer()

    override fun finished(description: Description) {
        super.finished(description)

        mockWebServer.shutdown()
    }

    fun mockWebServerResponse(body: String, code: Int) {
        mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(code))
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Service> createTestService(): Service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
        .create(Service::class.java)

}

fun RemoteTestRule.toServerSuccessResponse(jsonFile: String) {
    val body = readFromJSONToString(jsonFile)
    mockWebServerResponse(body, HttpURLConnection.HTTP_OK)
}

fun RemoteTestRule.toServerErrorResponse(jsonFile: String) {
    val body = readFromJSONToString(jsonFile)
    mockWebServerResponse(body, HttpsURLConnection.HTTP_BAD_REQUEST)
}