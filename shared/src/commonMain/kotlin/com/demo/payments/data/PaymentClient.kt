package com.demo.payments.data

import com.demo.payments.models.dto.RemoteException
import com.demo.payments.models.dto.RemoteExceptionType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.EMPTY
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.js.JsExport


@JsExport
object AppConfig {
    var FLAVOR = "debug"
    var host = ""
    var clientId: String = ""
    var clientSecret: String = ""
    var accessToken: String = ""
}

/*Simple data class to enforce build parameters*/
@JsExport
data class AppBuildParams(
    val host: String,
    val clientId: String,
    val clientSecret: String,
    val accessToken: String = "",
)

class PaymentClientConfig {
    fun createPrepaidHttpClient(
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

        install(AuthPlugin)

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


/**
 * mapOf(
 *     "api.uat3.test.aws.company.com" to  setOf(
 *         "sha256/dummy")
 * )
 * */
// TODO:
val uatPinsMap: Map<String, Set<String>> = emptyMap()


/**
 * mapOf(
 *     "api.aws.company.com" to  setOf(
 *         "sha256/dummy")
 * )
 * */
// TODO:
val productionPinsMap: Map<String, Set<String>> = emptyMap()