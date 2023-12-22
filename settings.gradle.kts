enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("http://dl.bintray.com/kotlin/kotlin-js-wrappers")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers/")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("http://dl.bintray.com/kotlin/kotlin-js-wrappers")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers/")
        }
    }
}

rootProject.name = "multiplatform-payments-fe"
include(":androidApp")
include(":shared")