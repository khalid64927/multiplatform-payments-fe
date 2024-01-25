import com.demo.payments.context.KotlinJSAppDependencies
import com.demo.payments.context.KotlinJSAppDependenciesContext
import com.demo.payments.data.AppBuildParams
import kotlinx.coroutines.InternalCoroutinesApi
import react.child
import react.dom.render

@InternalCoroutinesApi
fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        // TODO provide valid host, clientID and clientSecret
        val buildParams = AppBuildParams(
            host = "<your-host>",
            clientId = "<your-client-ID>",
            clientSecret = "<your-client-Secret>")
        KotlinJSAppDependenciesContext.Provider(KotlinJSAppDependencies(buildParams)) {
            child(App)
        }
    }
}