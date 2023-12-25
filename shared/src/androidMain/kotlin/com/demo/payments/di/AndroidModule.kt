package com.demo.payments.di


import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val androidAppModule = module {
    single<HttpClientEngine> { getEngine() }
}
