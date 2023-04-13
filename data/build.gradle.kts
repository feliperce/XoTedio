import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.protobuf") version "0.8.17"
}

val endPointUrl = "http://www.boredapi.com/"

android {
    namespace = "${Config.namespace}.data"
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "ENDPOINT_URL", "\"$endPointUrl\"")
        }
        debug {
            buildConfigField("String", "ENDPOINT_URL", "\"$endPointUrl\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)

    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)

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

    // DataStore
    implementation(Dependencies.Androidx.DataStore.proto)
    implementation(Dependencies.Google.Protobuf.javalite)

    // Room
    implementation(Dependencies.Androidx.Room.runtime)
    kapt(Dependencies.Androidx.Room.compiler)
    implementation(Dependencies.Androidx.Room.ktx)
}

protobuf {
    protoc {
        // find latest version number here:
        // https://mvnrepository.com/artifact/com.google.protobuf/protoc
        artifact = "com.google.protobuf:protoc:3.21.7"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins{
                create("java") {
                    option("lite")
                }
            }
        }
    }
}