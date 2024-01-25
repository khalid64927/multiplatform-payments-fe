package com.demo.payments.data.repository


import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.request.AuthenticateRequest
import com.demo.payments.models.dto.AuthenticateResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.content.TextContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import com.demo.payments.data.config.Result

internal class PaymentsRepositoryImpl(
    private val httpClient: HttpClient,
    private val authClient: HttpClient,
    private val log: co.touchlab.kermit.Logger,
    ): PaymentsRepository {

    val ktorTag = "PaymentsRepositoryImpl"

    @OptIn(InternalAPI::class)
    override suspend fun authenticate(config: RequestConfig<AuthenticateRequest>): Result<AuthenticateResponse> {
        return httpClient.safeRequest {
            contentType(ContentType.Application.FormUrlEncoded)
            method = HttpMethod.Post
            url {
                appendPathSegments(config.urlPath)
            }
            headers {
                for ((key, value) in config.headerMap) {
                    log.d("Headers: key=$key value=$value")
                    append(key, value)
                }
            }
            body =
                TextContent("grant_type=client_credentials", ContentType.Application.FormUrlEncoded)
        }
    }
}

