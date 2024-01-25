package com.demo.payments.di

import com.demo.payments.data.AppBuildParams

fun initKoin() {
    initKoin({
        modules(iosAppModule)
    }, AppBuildParams("", "", ""))
}
