package com.demo.payments.data.repository

import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.config.onFailure
import com.demo.payments.data.config.onSuccess
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise


class PaymentsJsRepositoryImpl(private val paymentsRepository: PaymentsRepository): PaymentsJsRepository {

    @OptIn(DelicateCoroutinesApi::class)
    override fun authenticate(config: RequestConfig<AuthenticateRequest>): Promise<AuthenticateResponse> {
        return Promise { resolve, reject ->
            GlobalScope.promise {

                return@promise paymentsRepository.authenticate(config)
            }.then {
                it.onSuccess(resolve).onFailure(reject)
            }
        }
    }


}

@JsExport
interface PaymentsJsRepository {
    fun authenticate(config: RequestConfig<AuthenticateRequest>): Promise<AuthenticateResponse>
}

