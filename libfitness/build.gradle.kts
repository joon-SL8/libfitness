import co.touchlab.skie.configuration.FlowInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("maven-publish")
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.touchlabs.skie)
}

val frameworkName = projects.libfitness.name

group = "com.skjline.fitness"
version = "0.1.0-SNAPSHOT"

kotlin {
    jvmToolchain(26)

    println("name: $frameworkName")
    val xcf = XCFramework(frameworkName)

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = frameworkName
            isStatic = true
            xcf.add(this)

            binaryOption("bundleId", "${projects.libfitness.group.orEmpty()}.$frameworkName")
            binaryOption("bundleVersion", "1.0.0")
        }
    }

    android {
        namespace = group.toString()
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        androidResources.enable = true
    }

    sqldelight {
        databases {
            create("FitnessDatabase") {
                packageName = "com.skjline.fitness.data.storage"
            }
        }
    }

    skie {
        features {
            group {
                FlowInterop.Enabled(true)
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)

//            implementation(libs.koin.android)
//            implementation(libs.koin.androidx.compose)
            implementation(libs.sqldelight.android)

            implementation(libs.ktor.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native)
            implementation(libs.ktor.darwin)
        }

        commonMain.dependencies {
            // foundation
            implementation(libs.kotlinx.material3)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)

            // ui
//            implementation(compose.ui)
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.components.uiToolingPreview)
//            implementation(libs.compose.calendar)

//            implementation(libs.androidx.lifecycle.viewmodel)

            // di
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // asset file handler
            implementation(libs.okio)
            implementation(libs.kotlinx.resources)

            // navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transition)

            // io
            implementation(libs.kable.core)
            implementation(libs.ktor.auth)
            implementation(libs.ktor.core)
            implementation(libs.ktor.content)
            implementation(libs.ktor.json)
            implementation(libs.ktor.network)
            implementation(libs.ktor.tls)

            // database
            implementation(libs.sqldelight.async)
            implementation(libs.sqldelight.coroutine)

            // util
            implementation(libs.kotlinx.datetime)
            implementation(libs.touchlabs.skie.options)
        }
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "${group}.resources"
    generateResClass = auto
}
