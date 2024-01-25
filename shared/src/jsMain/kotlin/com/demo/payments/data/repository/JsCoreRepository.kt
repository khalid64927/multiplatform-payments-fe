package com.demo.payments.data.repository

import com.demo.payments.data.config.JSRequestConfig
import com.demo.payments.data.config.onFailure
import com.demo.payments.data.config.onSuccess
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

@JsExport
class JsCoreRepository(private val coreRepository: CoreRepository) {

    @OptIn(DelicateCoroutinesApi::class)
    fun invokeApi(
        config: JSRequestConfig
    ): Promise<String> = Promise { resolve, reject ->
        GlobalScope.promise {
            coreRepository.invokeApi(config).onSuccess {
                resolve(it)
            }.onFailure {
                reject(it)
            }
        }
    }
}