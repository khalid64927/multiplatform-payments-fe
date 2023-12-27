package com.demo.payments.data.config

import io.ktor.http.HttpHeaders
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
data class RequestConfig<T>(
    val urlPath: String = "",
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