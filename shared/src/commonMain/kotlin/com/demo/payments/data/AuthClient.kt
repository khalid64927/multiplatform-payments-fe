package com.demo.payments.data

import com.demo.payments.data.config.RequestConfig
import com.demo.payments.data.config.Result
import com.demo.payments.data.repository.PaymentsRepository
import com.demo.payments.models.dto.RemoteException
import com.demo.payments.models.dto.RemoteExceptionType
import com.demo.payments.utils.getBasicAuth
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthClientConfig {
    fun createApiGeeHttpClient(
        httpClientEngine: HttpClientEngine,
        log: co.touchlab.kermit.Logger,
    ) = HttpClient(httpClientEngine) {
        expectSuccess = true
        install(Resources)
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000 // Set the connection timeout in milliseconds
        }

        exceptionHandling()
        install(Logging) {
            level = LogLevel.ALL
            logger = if (AppConfig.FLAVOR.isNotEmpty()) {
                object : Logger {
                    override fun log(message: String) {
                        log.i {message}
                    }
                }
            } else {
                Logger.EMPTY
            }
        }
        defaultRequest {
            url {
                host = AppConfig.host
                protocol = URLProtocol.HTTPS
            }

        }
    }

    private fun <T : HttpClientEngineConfig> HttpClientConfig<T>.exceptionHandling() {
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                val type = when (exception) {
                    is ClientRequestException -> RemoteExceptionType.CLIENT_ERROR
                    is ServerResponseException -> RemoteExceptionType.SERVER_ERROR
                    is JsonConvertException -> RemoteExceptionType.PARSING_ERROR
                    else -> RemoteExceptionType.UNKNOWN
                }
                throw RemoteException(type)
            }
        }
    }
}

val AuthPlugin = createClientPlugin(name = "AuthPlugin", ::AuthPluginConfig) {
    on(Send) { request ->
        if(AppConfig.accessToken.isNotEmpty()) {
            request.headers.append("Authorization", "Bearer ${AppConfig.accessToken}")
        }
        var originalCall = proceed(request)
        originalCall.response.request.url
        originalCall.response.run { // this: Htt
            println("response :: ${this.status.value}")
            when(status.value) {
                400, 401, 403 -> {
                    if(AppConfig.accessToken.isNotEmpty()) {
                        println("accessToken is available")
                        return@on originalCall
                    }
                    // Handle Unauthorized (status code 401)
                    // Handle Forbidden (status code 403)
                    withContext(Dispatchers.Main){
                        println("reAuth")
                        val hardcodedAuth = getBasicAuth()
                        val result = pluginConfig.repository.authenticate(
                            RequestConfig(
                                headerMap = mapOf(HttpHeaders.Authorization to hardcodedAuth),
                                urlPath = "/api/sg/v1/oauth/token",
                            )
                        )
                        if(result is Result.Success) {
                            AppConfig.accessToken = result.data.accessToken
                            request.headers.append("Authorization", "Bearer ${AppConfig.accessToken}")
                            originalCall = proceed(request)
                        }
                    }
                }
                // Add more cases as needed
                else -> {
                    //no-op
                }
            }
        }
        originalCall
    }
}

class AuthPluginConfig: KoinComponent {
    val repository: PaymentsRepository by inject()

}