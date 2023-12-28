# Multiplatform Ktor Repository Module

![kotlin-version](https://img.shields.io/badge/kotlin-1.9.20-blue)

**Kotlin Multiplatform** project with SwiftUI, Jetpack Compose, Compose for Wear OS, Compose for Desktop, Compose for Web, and Kotlin/JS + React clients along with Ktor backend. Currently running on
* Android (Jetpack Compose)
* iOS (Compose for iOS - experimental support!)
* Web (Kotlin/JS + React Wrapper)

## Overview

This Project provides Repository Layers that uses Ktor under the hood to provide networking and can
be used in Web, iOS and Android platforms


## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Android](#android)
  - [iOS](#ios)
  - [JavaScript](#javascript)
- [Languages, libraries and tools used](#Libraries)
- [Contributing](#contributing)
- [License](#license)
- [Changelog](#changelog)
- [Acknowledgments](#acknowledgments)

## Features
The repo provides ApiGeeInteractor that does Authentication.
Under the hood it calls PaymentsRepository

This repo currently is only a showcase for making only one API.

TODO: Make Generic Interactor that can work for all rest apis.

ALl three platform require some data by the library

*host* is needed only if you have same host for all apis

*clientId* and *clientSecret* are needed if you have Oauth call to get Token.
If this data is given Repo layer will take care to token generation and expiry.

# JS Platform

Use AppDependenciesContext and provide necessary data to Library

```kotlin
val appConfig = AppConfig(
            host = "",
            clientId = "",
            clientSecret = "")
        AppDependenciesContext.Provider(AppDependencies(appConfig)) {
            child(App)
        }
```
# Android
Call initKoin in Android Apps Application class or in any component during app launch time.

```kotlin
initKoin ({
            androidLogger()
            modules(androidAppModule)
            // TODO provide valid host, clientID and clientSecret
        }, AppConfig("", "", ""))

```
# iOS

```swift
@main
struct iOSApp: App {
    init() {
        HelperKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
```

## Getting Started
TODO

### Prerequisites
- [Kotlin](https://kotlinlang.org/)
- [Android Studio](https://developer.android.com/studio) (for Android development)
- [JDK 17](https://openjdk.org/projects/jdk/17/)
- [Gradle (8.5 and above)](https://docs.gradle.org/8.5/release-notes.html)
- [Xcode](https://developer.apple.com/xcode/) (for iOS development)
- [Node.js](https://nodejs.org/) (for JavaScript development)

### Installation

```bash
# Clone the repository
git clone https://github.com/khalid64927/multiplatform-payments-fe.git

# Change into the project directory
cd multiplatform-payments-fe

# Build and run the project
./gradlew build

## Usage
Android

```bash
./gradlew :androidApp:installDebug
```

iOS
```bash
./gradlew :iosApp:installDebug
```

Web
run below command to see demo JS app
```bash
./gradlew :web:browserDevelopmentRun
```
App will open your default web browser and load the page.
Now open Web Developer tools and observe console logs you should see as below for successful
authenticate call (remember to provide AppConfig values)

![plot](./assets/screenshots/web-api-success.png)

run below command to see demo JS app to see instant changes you make
```bash
./gradlew :web:browserDevelopmentRun --continuous
```
To generate npm binary from shared module
```bash
./gradlew shared:packJsPackage
```

## Libraries

* [Kotlin](https://kotlinlang.org/)
* [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
* [Ktor client library](https://github.com/ktorio/ktor)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* [Koin](https://github.com/InsertKoinIO/koin)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)
* [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)
* [Kotlin React](https://github.com/JetBrains/kotlin-wrappers)
* [Kotlin React Dom](https://github.com/JetBrains/kotlin-wrappers)
* [Kotlin React Router Dom](https://github.com/JetBrains/kotlin-wrappers)
* [Kotlin Styled](https://github.com/JetBrains/kotlin-wrappers)
* [NPM Publish](https://github.com/mpetuska/npm-publish)
* [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization)
* [Kermit](https://github.com/touchlab/Kermit)
* [Kotlin HTML](https://github.com/Kotlin/kotlinx.html)
* [JavaScript Material UI](https://github.com/mui/material-ui)

## Contributing
TODO

## License
TODO

## Changelog
TODO

## Acknowledgments
* [Mohammed Khalid Hamid] (https://github.com/khalid64927)