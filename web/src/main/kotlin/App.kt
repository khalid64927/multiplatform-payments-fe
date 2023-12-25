import com.demo.payments.context.AppDependenciesContext
import com.demo.payments.data.config.RequestConfig
import components.*
import components.materialui.AppBar
import components.materialui.Grid
import components.materialui.Toolbar
import kotlinx.coroutines.*
import kotlinx.css.margin
import kotlinx.css.px
import react.*
import styled.css


@InternalCoroutinesApi
val App = functionalComponent<RProps> {
    val appDependencies = useContext(AppDependenciesContext)
    var repository = appDependencies.repository

    useEffectWithCleanup(dependencies = listOf()) {
        val mainScope = MainScope()

        mainScope.launch {
            console.log(" functionalComponent :: mainScope :: launch ")
            // TODO: make api call with repository
            /**
             * repository.authenticate()
             * */
            console.log(" authenticate ")
        }
        return@useEffectWithCleanup { mainScope.cancel() }
    }
    Fragment {
        AppBar {
            css {
                margin(0.px)
            }
            Toolbar {
                Typography("h6", "People In Space")
            }
        }

        Toolbar {
            // Empty toolbar to avoid below content to be overlapped by AppBar
        }

        Grid {
            attrs {
                container = true
                spacing = 4
                justify = "flex-start"
                alignItems = "stretch"
            }

            Grid {
                attrs {
                    item = true
                    md = 4
                    xs = 12
                }
            }
            Grid {
                attrs {
                    item = true
                    md = 8
                    xs = 12
                }
            }
        }
    }
}