package com.demo.payments.data.repository

import co.touchlab.kermit.Logger
import com.demo.payments.data.config.ApiException
import com.demo.payments.data.config.Result
import com.demo.payments.di.baseLogger
import com.demo.payments.models.dto.ErrorDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json



fun getLogger(tag: String) = baseLogger.withTag(tag)

suspend inline fun <reified T> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): Result<T> {
    var httpResponse: HttpResponse? = null
    var result: Result<T> = Result.Failure(Exception("Something went wrong !"))
    runCatching {
        httpResponse = request { block() }
        getLogger("safeRequest").d("httpResponse is initialised")
        val response = httpResponse?.body() as T
        getLogger("safeRequest").d("safeRequest end $response")
        result = Result.Success(response)
    }.onFailure {
        getLogger("safeRequest").d("safeRequest request completed")
        var errorDto = httpResponse?.getErrorDto()
        result = Result.Failure(
            ApiException(
                errorMessage = it.message,
                error = it,
                responseBody = errorDto
            )
        )
    }
    return result
}

suspend fun HttpResponse.getErrorDto(): ErrorDto? {
    var errorDto: ErrorDto? = null
    runCatching {
        errorDto = Json.decodeFromString(this.body())
    }
    return errorDto
}