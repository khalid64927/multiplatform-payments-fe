package com.demo.payments.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class AuthenticateRequest(
    @SerialName("grant_type")
    val grant_type: String = "client_credentials")