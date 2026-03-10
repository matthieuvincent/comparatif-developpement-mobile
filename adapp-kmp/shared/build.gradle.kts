import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("co.touchlab.skie") version "0.10.9"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export("io.insert-koin:koin-core:4.1.1")
            export("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
        }
    }

    sourceSets {
        val koinVersion = "4.1.1"
        commonMain.dependencies {
            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            api("io.insert-koin:koin-core:${koinVersion}")
            api("io.insert-koin:koin-compose:${koinVersion}")
            api("io.insert-koin:koin-compose-viewmodel:${koinVersion}")
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)

        }
        androidMain.dependencies {
            val materialIconVersion = "1.7.8"
            val cameraVersion = "1.5.2"
            implementation("androidx.compose.material:material-icons-core:$materialIconVersion")
            implementation("androidx.compose.material:material-icons-extended:$materialIconVersion")
            implementation("io.insert-koin:koin-android:$koinVersion")
            implementation("com.google.android.gms:play-services-location:21.3.0")
            implementation("androidx.camera:camera-camera2:$cameraVersion")
            implementation("androidx.camera:camera-lifecycle:$cameraVersion")
            implementation("androidx.camera:camera-view:$cameraVersion")
            implementation("com.google.mlkit:barcode-scanning:17.3.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "lu.etat.adapp_kmp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
