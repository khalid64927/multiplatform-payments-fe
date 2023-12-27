package com.demo.payments.domain.interactor.apigee

import com.demo.payments.data.config.AppData
import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.config.Result
import com.demo.payments.data.config.onFailure
import com.demo.payments.data.config.onSuccess
import com.demo.payments.data.repository.PaymentsRepository
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse
import com.demo.payments.utils.Routes
import com.demo.payments.utils.getBasicAuth
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ApiGeeInteractorImpl(
    private val appData: AppData,
    private val repository: PaymentsRepository,
    private val log: co.touchlab.kermit.Logger,
): ApiGeeInteractor {
    override suspend fun invoke(): Flow<Result<AuthenticateResponse>> = flow {
        val configuration = RequestConfig<AuthenticateRequest>(
            headerMap = mapOf(
                HttpHeaders.Authorization to getBasicAuth(),
                HttpHeaders.ContentType to "application/x-www-form-urlencoded"),
            urlPath = Routes.AUTH,
        )
        repository.authenticate(configuration).
        onSuccess {
            log.d("onSuccess $it")
            appData.accessToken = it.accessToken
            emit(Result.Success(it))
        }.onFailure {
            log.d("onFailure $it")
            emit(Result.Failure(it))
        }

    }
}