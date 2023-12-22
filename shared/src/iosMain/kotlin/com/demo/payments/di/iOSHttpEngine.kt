package com.demo.payments.di

import com.demo.payments.data.config.BuildKonfig
import com.demo.payments.data.config.productionPinsMap
import com.demo.payments.data.config.uatPinsMap
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.certificates.CertificatePinner


internal fun getEngine() = Darwin.create {
        val pinMap = if (BuildKonfig.FLAVOR == "uat") uatPinsMap else productionPinsMap
        val builder = CertificatePinner.Builder()
        pinMap.forEach { pinMapItem ->
            val host = pinMapItem.key
            val pins = pinMapItem.value.toTypedArray()
            builder.add(host, *pins)
        }
        handleChallenge(builder.build())
    }



