import com.demo.payments.context.AppDependencies
import com.demo.payments.context.AppDependenciesContext
import kotlinx.coroutines.InternalCoroutinesApi
import react.child
import react.dom.render

@InternalCoroutinesApi
fun main() {
    render(kotlinx.browser.document.getElementById("root")) {
        AppDependenciesContext.Provider(AppDependencies) {
            child(App)
        }
    }
}