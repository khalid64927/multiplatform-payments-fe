package com.demo.payments.context

import com.demo.payments.data.AppConfig
import com.demo.payments.di.initKoin
import com.demo.payments.di.jsAppModule
import com.demo.payments.domain.interactor.apigee.ApiGeeInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext


@OptIn(ExperimentalJsExport::class)
@JsExport
class AppDependencies(appConfig: AppConfig) : KoinComponent {
    val apiGeeInteractor: ApiGeeInteractor
    init {
        initKoin({
            modules(jsAppModule)
        }, appConfig)
        apiGeeInteractor = get()
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
val AppDependenciesContext = createContext<AppDependencies>()