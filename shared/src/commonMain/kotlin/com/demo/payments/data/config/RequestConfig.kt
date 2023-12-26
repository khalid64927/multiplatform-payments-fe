package com.demo.payments.data.config

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

@OptIn(ExperimentalJsExport::class)
@JsExport
data class RequestConfig<T>(
    val urlPath: String? = null,
    val resource: Any? = null,
    val postBodyJson: T? = null,
    val headerMap: Map<String, String> = emptyMap(),
    val postBody: Map<String, String> = emptyMap(),
)