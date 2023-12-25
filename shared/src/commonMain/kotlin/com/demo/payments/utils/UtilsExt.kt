package com.demo.payments.utils

import com.demo.payments.data.BuildKonfig
import io.ktor.util.encodeBase64
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun getBasicAuth(): String{
    val clientId = BuildKonfig.clientId
    val clientSecret = BuildKonfig.clientSecret
    val base64Credential = "$clientId:$clientSecret".encodeBase64()
    return "Basic $base64Credential"
}

inline fun <reified T : Any> T.toJson(): String {
    val json = Json { encodeDefaults = true }
    return json.encodeToString(this)
}


object Routes {
    const val AUTH = "/api/sg/v1/oauth/token"
}