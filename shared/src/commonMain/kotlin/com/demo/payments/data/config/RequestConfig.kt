package com.demo.payments.data.config

import com.demo.payments.data.AppConfig
import kotlin.js.JsExport

data class RequestConfig<T>(
    val urlPath: String = "",
    val baseUrl: String = AppConfig.host,
    val resource: Any? = null,
    val postBodyJson: T? = null,
    val headerMap: Map<String, String> = emptyMap(),
    val postBody: Map<String, String> = emptyMap(),
)


@JsExport
data class JSRequestConfig(
    /*
    * Provide path segment
    * example:
    *
    * "payment/v1/balance"
    * */
    val pathSegment: String,

    /*
    * Provide base url
    *
    * example:
    * "api.aws.company.com"
    * Note protocol (HTTP/HTTPS) is added by default
    * */
    val baseUrl: String = AppConfig.host,

    /*
    * Provide method type
    * example:
    * GET, POST, PUT, DELETE
    * */
    var method: String,
    /*
    * Provide Json string for body json string
    * example:
    * {
    *   "deviceId": "abc",
    *   "platform": "android"
    * }
    * */
    val postBodyJson: String = "",

    /*
    * Provide header as Json string
    * example:
    * {
    *   "deviceId": "abc",
    *   "platform": "android"
    * }
    * */
    val headerJsonString: String = "{}",
)