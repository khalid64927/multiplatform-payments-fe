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
            val hardcodedAuth = "Basic SkJFTHh5a2dNR0FqSlZ3SGpmcVRBaTRXV2V3YmdjTU86R2g2MzUwMVA1VHFCR3VadQ=="
            repository?.authenticate(RequestConfig("api.uat3.test.aws.singtel.com/api/sg/v1/oauth/token", headerMap = mapOf("Authorization" to hardcodedAuth)))
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