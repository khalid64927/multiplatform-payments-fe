plugins {
    kotlin("js")
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation(libs.io.insert.koin.core)
    implementation(libs.touchlab.kermit)
    implementation(libs.org.jetbrains.kotlinx.coroutines.core)

    implementation(libs.kotlinx.html.js)

    implementation(libs.kotlin.react)
    implementation(libs.kotlin.react.dom)
    implementation(libs.kotlin.react.router.dom)
    implementation(libs.kotlin.styled)
    implementation(npm("react", "16.13.0"))
    implementation(npm("react-dom", "16.13.0"))

    // Material Design Components for React
    implementation(npm("@material-ui/core", "4.11.1"))

    implementation(projects.shared)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
}

afterEvaluate {
    rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
        versions.webpackCli.version = "4.10.0"
    }
}
