package com.demo.payments.di


import com.demo.payments.data.BuildKonfig
import com.demo.payments.data.productionPinsMap
import com.demo.payments.data.uatPinsMap
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import javax.net.ssl.HttpsURLConnection


internal fun getEngine(): HttpClientEngine {
    return OkHttp.create {
        // TLS specs for clear text
        val clearSpec = listOf(
            ConnectionSpec.CLEARTEXT,
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledTlsVersions()
                .allEnabledCipherSuites()
                .build(),
        )
        // Pinning
        // TODO: replace BuildKonfig with gradle Properties
        val pinMap = if(BuildKonfig.FLAVOR == "uat") uatPinsMap else productionPinsMap
        val builder = CertificatePinner.Builder()
        pinMap.forEach { pinMapItem ->
            val host = pinMapItem.key
            val pins = pinMapItem.value.toTypedArray()
            builder.add(host, *pins)
        }
        val certificatePinner = builder.build()
        val client = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .connectionSpecs(clearSpec)
            .build()
        preconfigured = client

    }
}

internal fun getAndroidEngine(): HttpClientEngine {
    val engine = Android.config {
        sslManager  = { urlConnection ->
            sslCheck(urlConnection)
        }
    }
    return engine.create()
}

private fun sslCheck(urlConnection: HttpsURLConnection){
    println("sslCheck ")
    urlConnection.run {
        println("usingProxy : ${usingProxy()}")
    }

}