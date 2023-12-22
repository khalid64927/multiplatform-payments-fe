package com.demo.payments.data.repository

import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.config.Result
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse

interface PaymentsRepository {
    suspend fun authenticate(
        config: RequestConfig<AuthenticateRequest>
    ): Result<AuthenticateResponse>
}

fun defaultMap() = emptyMap<String, String>()
fun addMap(headerMap: Map<String, String>) {
    val mutableMap = mutableMapOf<String, String>()
    mutableMap.putAll(headerMap)
}

