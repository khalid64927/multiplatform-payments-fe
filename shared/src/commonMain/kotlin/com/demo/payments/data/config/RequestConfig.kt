package com.demo.payments.data.config

import com.demo.payments.data.AppConfig
import io.ktor.http.HttpHeaders
import kotlin.js.JsExport

@JsExport
data class RequestConfig<T>(
    val urlPath: String = "",
    val baseUrl: String = AppConfig.host,
    val resource: Any? = null,
    val postBodyJson: T? = null,
    val headerMap: Map<String, String> = emptyMap(),
    val postBody: Map<String, String> = emptyMap(),
) {

    companion object {
        val AUTH_CONTENT_TYPE = mutableMapOf(
            HttpHeaders.ContentType to "application/x-www-form-urlencoded")
    }
}


@JsExport
data class JSRequestConfig(
    val pathSegment: String,
    val baseUrl: String = AppConfig.host,
    var method: String,
    val postBodyJson: String = "",
    val headerMap: Map<String, String> = emptyMap(),
)