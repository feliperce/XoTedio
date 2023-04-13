object Dependencies {

    object Androidx {
        const val core = "androidx.core:core-ktx:1.10.0"
        const val junit = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"


        object DataStore {
            const val proto = "androidx.datastore:datastore:1.0.0"
        }

        object Compose {
            const val bom = "androidx.compose:compose-bom:2023.01.00"
            const val foundation = "androidx.compose.foundation:foundation:1.4.1"
            const val ui = "androidx.compose.ui:ui"
            const val material = "androidx.compose.material:material"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val tooling = "androidx.compose.ui:ui-tooling"
            const val activity = "androidx.activity:activity-compose:1.7.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"
            const val livedata = "androidx.compose.runtime:runtime-livedata"
            const val iconsCore = "androidx.compose.material:material-icons-core"
            const val iconsExtended = "androidx.compose.material:material-icons-extended"
            const val constraint = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
            const val uiTestJunit = "androidx.compose.ui:ui-test-junit4"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
            const val navHost = "androidx.navigation:navigation-compose:2.5.3"
            const val material3 = "androidx.compose.material3:material3:1.1.0-beta02"
        }

        object Lifecycle {
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:2.3.0"
            const val compiler = "androidx.room:room-compiler:2.3.0"
            const val ktx = "androidx.room:room-ktx:2.3.0"
        }

    }

    object Google {
        object Protobuf {
            const val javalite = "com.google.protobuf:protobuf-javalite:3.21.7"
        }
    }

    object Junit {
        const val junit = "junit:junit:4.13.2"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:4.9.3"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.5.0"
        const val okHttp = "com.squareup.okhttp3:okhttp"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor"
    }

    object Koin {
        const val koin = "io.insert-koin:koin-android:3.2.0"
        const val compose = "io.insert-koin:koin-androidx-compose:3.2.0"
    }
}