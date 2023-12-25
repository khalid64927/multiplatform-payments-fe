package com.demo.payments.context

import com.demo.payments.data.repository.PaymentsJsRepository
import com.demo.payments.data.repository.PaymentsRepository
import com.demo.payments.di.initKoin
import com.demo.payments.di.jsAppModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext


@OptIn(ExperimentalJsExport::class)
@JsExport
object AppDependencies: KoinComponent {
    var repository: PaymentsRepository?
    var repositoryJs: PaymentsJsRepository?
    init {
        initKoin {
            modules(jsAppModule)
        }
        repository = get()
        repositoryJs = get()
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
val AppDependenciesContext = createContext<AppDependencies>()