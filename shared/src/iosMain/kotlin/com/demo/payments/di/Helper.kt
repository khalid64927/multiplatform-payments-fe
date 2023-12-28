package com.demo.payments.di

import com.demo.payments.data.AppConfig

fun initKoin() {
    initKoin({
        modules(iosAppModule)
    },AppConfig("", "", ""))
}
