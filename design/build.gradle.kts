plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "${Config.namespace}.design"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = Config.composeKotlinCompilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)

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

}