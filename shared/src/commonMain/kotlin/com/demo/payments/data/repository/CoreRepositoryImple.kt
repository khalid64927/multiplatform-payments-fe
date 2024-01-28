package com.demo.payments.data.repository
import com.demo.payments.data.config.JSRequestConfig
import com.demo.payments.data.config.Result
import com.demo.payments.di.baseLogger
import com.demo.payments.utils.toJson
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json


internal class CoreRepositoryImple(
    private val httpClient: HttpClient,
    private val authClient: HttpClient,
    private val log: co.touchlab.kermit.Logger,
): CoreRepository {

    val ktorTag = "CoreRepositoryImple"

    @OptIn(InternalAPI::class)
    override suspend fun invokeApi(config: JSRequestConfig): Result<String> =
        httpClient.safeRequest {
            contentType(ContentType.Application.Json)
            method = when(config.method.uppercase()){
                "POST" -> HttpMethod.Post
                "GET" -> HttpMethod.Post
                "PUT" -> HttpMethod.Put
                "DELETE" -> HttpMethod.Delete
                else -> throw IllegalStateException("Given method (${config.method})  is Invalid")
            }

            url {
                host = config.baseUrl
                appendPathSegments(config.pathSegment)
            }

            headers {
                for ((key, value) in getHeaderMap(config.headerJsonString)) {
                    log.d("Headers: key=$key value=$value")
                    append(key, value)
                }
            }

            if(config.postBodyJson.isNotEmpty()){
                body = config.postBodyJson.toJson()
            }
        }


    private fun getHeaderMap(headerJsonString: String): Map<String, String> {
        runCatching {
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString(headerJsonString)
        }.onFailure {
            baseLogger.withTag("CoreRepositoryImple").e { "Failed to read headers!" }
            return emptyMap()
        }
        return emptyMap()
    }
}


