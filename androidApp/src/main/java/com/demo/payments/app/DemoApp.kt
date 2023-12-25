package com.demo.payments.app

import android.app.Application
import com.demo.payments.data.AppConfig
import com.demo.payments.di.androidAppModule
import com.demo.payments.di.initKoin
import org.koin.android.ext.koin.androidLogger

class DemoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin ({
            androidLogger()
            modules(androidAppModule)
        }, AppConfig("", "", ""))
    }
}