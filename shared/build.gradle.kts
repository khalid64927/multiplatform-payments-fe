plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.npmPublish)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    js(IR) {
        binaries.library()
        useCommonJs()
        browser()
        generateTypeScriptDefinitions()
    }
    
    sourceSets {

        all {
            languageSettings.apply {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.bundles.ktor)
                implementation(libs.org.jetbrains.kotlinx.datetime)
                implementation(libs.io.insert.koin.core)
                implementation(libs.touchlab.kermit)
            }

        }
        val androidMain by getting {
            dependencies {
                implementation(libs.io.ktor.client.android)
                implementation(libs.io.ktor.client.okhttp)
                implementation(libs.io.insert.koin.android)
            }

        }

        val jsMain by getting {
            dependencies {
                implementation(libs.org.jetbrains.kotlinx.coroutines.core)
                implementation(libs.io.ktor.client.js)
                implementation(kotlin("stdlib-js"))
                implementation(libs.kotlin.react)
                implementation(libs.kotlin.react.dom)
                implementation(libs.kotlin.react.router.dom)
                implementation(libs.kotlin.styled)
                implementation(npm("react", "16.13.0"))
                implementation(npm("react-dom", "16.13.0"))

            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.org.jetbrains.kotlinx.coroutines.test)
                implementation(libs.kotest.assertions)
                implementation(libs.turbine)
            }
        }

        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(libs.io.ktor.client.darwin)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

    }


}

android {
    namespace = "com.demo.payments.app"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}