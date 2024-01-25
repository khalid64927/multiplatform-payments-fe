package com.demo.payments.data.repository

import com.demo.payments.data.config.JSRequestConfig
import com.demo.payments.data.config.Result


interface CoreRepository {

    suspend fun invokeApi(
        config: JSRequestConfig
    ): Result<String>
}