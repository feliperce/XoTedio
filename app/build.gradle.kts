import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = Config.namespace
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.namespace
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)

    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)
    androidTestImplementation(Dependencies.Androidx.espresso)

    val composeBom = platform(Dependencies.Androidx.Compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(Dependencies.Androidx.Compose.foundation)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.toolingPreview)
    debugImplementation(Dependencies.Androidx.Compose.tooling)
    androidTestImplementation(Dependencies.Androidx.Compose.uiTestJunit)
    debugImplementation(Dependencies.Androidx.Compose.uiTestManifest)
    implementation(Dependencies.Androidx.Compose.activity)
    implementation(Dependencies.Androidx.Compose.viewModel)
    implementation(Dependencies.Androidx.Compose.livedata)
    implementation(Dependencies.Androidx.Compose.iconsCore)
    implementation(Dependencies.Androidx.Compose.iconsExtended)
    implementation(Dependencies.Androidx.Compose.constraint)
    implementation(Dependencies.Androidx.Compose.navHost)
    implementation(Dependencies.Androidx.Compose.material3)

    // Koin
    implementation(Dependencies.Koin.koin)
    implementation(Dependencies.Koin.compose)

    // Retrofit
    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.gsonConverter)
    // Okhttp
    val okHttpBom = platform(Dependencies.Square.okHttpBom)
    implementation(okHttpBom)
    implementation(Dependencies.Square.okHttp)
    implementation(Dependencies.Square.okHttpLogging)

    implementation(project(":design"))
    implementation(project(":home"))
    implementation(project(":data"))

}