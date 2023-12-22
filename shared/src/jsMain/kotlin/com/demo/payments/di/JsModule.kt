package com.demo.payments.di

import com.demo.payments.data.repository.PaymentsJsRepositoryImpl
import io.ktor.client.engine.js.Js
import org.koin.dsl.module

val appModule = module {
    single { Js.create() }
    single { PaymentsJsRepositoryImpl(get()) }
}
