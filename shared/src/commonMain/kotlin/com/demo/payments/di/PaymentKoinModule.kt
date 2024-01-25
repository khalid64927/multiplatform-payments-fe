package com.demo.payments.di

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.demo.payments.data.AppBuildParams
import com.demo.payments.data.AppConfig
import com.demo.payments.data.AuthClientConfig
import com.demo.payments.data.PaymentClientConfig
import com.demo.payments.data.repository.PaymentsRepository
import com.demo.payments.data.repository.PaymentsRepositoryImpl
import com.demo.payments.domain.interactor.apigee.ApiGeeInteractor
import com.demo.payments.domain.interactor.apigee.ApiGeeInteractorImpl
import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
    buildParams: AppBuildParams
) = startKoin {
    validate(buildParams)
    // save build params in app config object
    buildParams.run {
        AppConfig.FLAVOR = "debug"
        AppConfig.host = host
        AppConfig.clientId = clientId
        AppConfig.clientSecret = clientSecret
    }
    appDeclaration()
    modules(
        prepaidDataModule,
        prepaidDomainModule
    )
}

val prepaidDomainModule = module {
    factory<ApiGeeInteractor> { ApiGeeInteractorImpl(
        repository = get(),
        log = getWith<Logger>("Interactor").
    withTag("ApiGeeInteractor"))
    }
}

fun validate(buildParams: AppBuildParams){
    buildParams.run {
        if(host.trim().isEmpty() || clientId.trim().isEmpty() || clientSecret.trim().isEmpty()){
            throw IllegalStateException("Either or all configs (host or clientId or clientSecret) are not set !.\n" +
                    "Please provide all these values!")
        }
    }
}

val prepaidDataModule = module {
    factory { (tag: String? ) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }

    // This client is used authenticating with apigee
    single<HttpClient> (named("authClient")) {
        val client = AuthClientConfig()
        client.createApiGeeHttpClient(
            httpClientEngine = get(),
            log = getWith<Logger>(
                "Prepaid-Ktor").
            withTag("Payments-Ktor-Client")
        )
    }

    // This client is used for all api connection
    single<HttpClient> (named("paymentsClient")) {
        val client = PaymentClientConfig()
        client.createPrepaidHttpClient(
            httpClientEngine = get(),
            log = getWith<Logger>(
                "Payments-Ktor").
            withTag("Payments-Ktor-Client")
        )
    }

    single<PaymentsRepository> {
        PaymentsRepositoryImpl(
            httpClient = get( qualifier = named("paymentsClient")),
            authClient = get( qualifier = named("authClient")),
            log = getWith<Logger>(
                "Repository").
            withTag("PaymentsRepository")
        )
    }
}

// platformLogWriter() is a relatively simple config option, useful for local debugging. For production
// uses you *may* want to have a more robust configuration from the native platform. In KaMP Kit,
// that would likely go into platformModule expect/actual.
// See https://github.com/touchlab/Kermit
val baseLogger = Logger(config = StaticConfig(logWriterList = listOf(platformLogWriter())), "PaymentsLog")

inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

// Simple function to clean up the syntax a bit
fun KoinComponent.injectLogger(tag: String = "Ktor"): Lazy<Logger> = inject { parametersOf(tag) }


