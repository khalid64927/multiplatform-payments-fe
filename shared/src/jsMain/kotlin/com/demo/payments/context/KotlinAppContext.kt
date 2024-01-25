package com.demo.payments.context

import com.demo.payments.data.AppBuildParams
import com.demo.payments.di.initKoin
import com.demo.payments.di.jsAppModule
import com.demo.payments.domain.interactor.apigee.ApiGeeInteractor
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext

/*This Context is for Kotlin JS Apps*/
class KotlinJSAppDependencies(buildParams: AppBuildParams) : KoinComponent {

    val apiGeeInteractor: ApiGeeInteractor

    init {
        initKoin({
            modules(jsAppModule)
        }, buildParams)
        apiGeeInteractor = get()
    }
}

val KotlinJSAppDependenciesContext = createContext<KotlinJSAppDependencies>()