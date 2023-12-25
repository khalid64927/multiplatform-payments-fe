import com.demo.payments.context.AppDependencies
import com.demo.payments.context.AppDependenciesContext
import com.demo.payments.data.AppConfig
import kotlinx.coroutines.InternalCoroutinesApi
import react.child
import react.dom.render

@InternalCoroutinesApi
fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        // TODO provide valid host, clientID and clientSecret
        val appConfig = AppConfig(host = "", clientId = " ", clientSecret = "")
        AppDependenciesContext.Provider(AppDependencies(appConfig)) {
            child(App)
        }
    }
}