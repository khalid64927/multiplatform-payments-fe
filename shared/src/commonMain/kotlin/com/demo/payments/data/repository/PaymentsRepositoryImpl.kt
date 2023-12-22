package com.demo.payments.data.repository


import com.demo.payments.data.config.ApiException
import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse
import com.demo.payments.models.dto.ErrorDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json
import com.demo.payments.data.config.Result.Failure
import com.demo.payments.data.config.Result.Success
import com.demo.payments.data.config.Result

class PaymentsRepositoryImpl(
    private val httpClient: HttpClient,
    private val authClient: HttpClient,
    ): PaymentsRepository {


    @OptIn(InternalAPI::class)
    override suspend fun authenticate(config: RequestConfig<AuthenticateRequest>): Result<AuthenticateResponse> {
        return authClient.safeRequest {
            contentType(ContentType.Application.FormUrlEncoded)
            method = HttpMethod.Post
            url {
                appendPathSegments("/api/sg/v1/oauth/token")
            }
            headers {
                for ((key, value) in config.headerMap) {
                    append(key, value)
                }
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
            }
            body =
                TextContent("grant_type=client_credentials", ContentType.Application.FormUrlEncoded)
        }
    }

    private suspend inline fun <reified T> HttpClient.safeRequest(
        block: HttpRequestBuilder.() -> Unit,
    ): Result<T> {
        var httpResponse: HttpResponse? = null
        var result: Result<T> = Failure(Exception("Something went wrong !"))
        runCatching {
            httpResponse = request { block() }
            println("httpResponse is initialised")
            val response = httpResponse?.body() as T
            println("safeRequest end $response")
            result = Success(response)
        }.onFailure {
            println("safeRequest request completed")
            var errorDto = httpResponse?.getErrorDto()
            result = Failure(
                ApiException(
                    errorMessage = it.message,
                    error = it,
                    responseBody = errorDto
                )
            )
        }
        return result
    }

    private suspend fun HttpResponse.getErrorDto(): ErrorDto? {
        var errorDto: ErrorDto? = null
        runCatching {
            errorDto = Json.decodeFromString(this.body())
        }
        return errorDto
    }
}

