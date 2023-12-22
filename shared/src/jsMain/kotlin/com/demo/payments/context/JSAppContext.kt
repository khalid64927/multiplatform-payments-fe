package com.demo.payments.context

import com.demo.payments.data.repository.PaymentsJsRepository
import com.demo.payments.di.appModule
import com.demo.payments.di.initKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import react.createContext


@OptIn(ExperimentalJsExport::class)
@JsExport
object AppDependencies: KoinComponent {
    val repository: PaymentsJsRepository
    init {
        initKoin {
            appModule
        }
        repository = get()
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
val AppDependenciesContext = createContext<AppDependencies>()