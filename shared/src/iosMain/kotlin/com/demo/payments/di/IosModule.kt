package com.demo.payments.di


import org.koin.dsl.module

val appModule = module {
    single { getEngine() }
}
