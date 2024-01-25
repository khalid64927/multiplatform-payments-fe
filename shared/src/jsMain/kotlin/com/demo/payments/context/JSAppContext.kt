package com.demo.payments.context

import com.demo.payments.data.AppBuildParams
import com.demo.payments.data.repository.JsCoreRepository
import com.demo.payments.di.initKoin
import com.demo.payments.di.jsAppModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext


/*This Context is for JS Apps*/
@JsExport
class JsAppDependencies(buildParams: AppBuildParams) : KoinComponent {

    val repository: JsCoreRepository

    init {
        initKoin({
            modules(jsAppModule)
        }, buildParams)
        repository = get()
    }
}

@JsExport
val JSAppDependenciesContext = createContext<JsAppDependencies>()