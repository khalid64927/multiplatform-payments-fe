# Multiplatform Ktor Repository Module

![kotlin-version](https://img.shields.io/badge/kotlin-1.9.20-blue)

**Kotlin Multiplatform** project with SwiftUI, Jetpack Compose, Compose for Wear OS, Compose for Desktop, Compose for Web, and Kotlin/JS + React clients along with Ktor backend. Currently running on
* Android (Jetpack Compose)
* iOS (Compose for iOS - experimental support!)
* Web (Kotlin/JS + React Wrapper) (contributed by https://github.com/PatilShreyas)

It makes use of [Open Notify PeopleInSpace API](http://open-notify.org/Open-Notify-API/People-In-Space/) to show list of people currently in
space and also the position of the International Space Station (inspired by https://kousenit.org/2019/12/19/a-few-astronomical-examples-in-kotlin/)!

The project is included as sample in the official [Kotlin Multiplatform Mobile docs](https://kotlinlang.org/docs/mobile/samples.html#peopleinspace) and also the [Google Dev Library](https://devlibrary.withgoogle.com/products/android)

### Building

### Compose for Web client (JS)

The Compose for Web client resides in the `compose-web` module and can be run by
invoking `./gradlew :compose-web:jsBrowserDevelopmentRun`

### Compose for iOS client

TODO

### Screenshots

TODO

**Android (Jetpack Compose)**
TODO

**Web App (Kotlin/JS + React)**
TODO


### Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
* [Ktor client library](https://github.com/ktorio/ktor)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* [Koin](https://github.com/InsertKoinIO/koin)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)
* [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)
* [Coil](https://coil-kt.github.io/coil/)
