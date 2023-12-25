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


object BuildKonfig {
    var FLAVOR = "debug"
    /**
     * UAT : api.uat3.test.aws.singtel.com
     * prod: www.api.singtel.com
    */
    var HOST = "api.uat3.test.aws.singtel.com"
    var clientId: String = ""
    var clientSecret: String = ""
}

data class AppConfig(val host: String, val clientId: String, val clientSecret: String)

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
            logger = if (BuildKonfig.FLAVOR.isNotEmpty()) {
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
                host = BuildKonfig.HOST
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


val uatPinsMap: Map<String, Set<String>> = mapOf(
    "api.uat3.test.aws.singtel.com" to  setOf(
        "sha256/yevOlLVhW6H9Zco95gQsObqRDLvXfssMBvAFYQvjUrk=",
        "sha256/q+/wkbAp7eqw63n/j5nXubRRmg1kkl4uJ2mY3n3FHa0=",
        "sha256/cGuxAXyFXFkWm61cF4HPWX8S0srS9j0aSqN0k4AP+4A=")
)

val productionPinsMap: Map<String, Set<String>> = mapOf(
    "api.aws.singtel.com" to  setOf("sha256/++AAA=======================================")
)