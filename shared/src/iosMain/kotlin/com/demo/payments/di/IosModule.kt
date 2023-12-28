package com.demo.payments.di


import org.koin.dsl.module

val iosAppModule = module {
    single { getEngine() }
}
