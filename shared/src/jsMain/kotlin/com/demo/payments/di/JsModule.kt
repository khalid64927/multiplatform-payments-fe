package com.demo.payments.di

import com.demo.payments.data.repository.PaymentsJsRepository
import com.demo.payments.data.repository.PaymentsJsRepositoryImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js
import org.koin.dsl.module

val jsAppModule = module {
    single<HttpClientEngine> { Js.create() }
    single<PaymentsJsRepository> { PaymentsJsRepositoryImpl(get()) }
}
