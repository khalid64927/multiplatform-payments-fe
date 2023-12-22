package com.demo.payments.domain.interactor.apigee

import com.demo.payments.data.config.AppData
import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.config.Result
import com.demo.payments.data.config.onFailure
import com.demo.payments.data.config.onSuccess
import com.demo.payments.data.repository.PaymentsRepository
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ApiGeeInteractorImpl(
    private val appData: AppData,
    private val repository: PaymentsRepository
): ApiGeeInteractor {
    override suspend fun invoke(): Flow<Result<AuthenticateResponse>> = flow {
        // TODO: set from parent app
        val clientId = "clientID"
        // TODO: set from parent app
        val clientSecret = "clientSecret"
        // TODO: remove hardcoded base64 string
        val hardcodedAuth = "Basic SkJFTHh5a2dNR0FqSlZ3SGpmcVRBaTRXV2V3YmdjTU86R2g2MzUwMVA1VHFCR3VadQ=="
        val configuration = RequestConfig<AuthenticateRequest>(
            headerMap = mapOf(HttpHeaders.Authorization to hardcodedAuth),
            urlPath = "/api/sg/v1/oauth/token",
        )
        repository.authenticate(configuration).
        onSuccess {
            println("onSuccess $it")
            appData.accessToken = it.accessToken
            println("onSuccess $it")
            emit(Result.Success(it))
        }.onFailure {
            emit(Result.Failure(it))
        }

    }
}