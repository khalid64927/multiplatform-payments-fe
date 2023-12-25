package com.demo.payments.data.config

import kotlin.js.Json
import kotlin.js.json

@OptIn(ExperimentalJsExport::class)
@JsExport
data class RequestConfigJs<T>(val urlPath: String,
                              val resource: Any? = null,
                              val postBodyJson: T? = null,
                              val headerJson: Json = json(),
                              val postBody: Json = json()) {


}