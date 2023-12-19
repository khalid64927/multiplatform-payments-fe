plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
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
        useCommonJs()
        browser()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor)
            //implementation(libs.org.jetbrains.kotlinx.datetime)
            //implementation(libs.org.jetbrains.kotlinx.coroutines.core)
            implementation(libs.io.insert.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.demo.payments"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
    }
}