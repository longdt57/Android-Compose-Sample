import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.service)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.pref)
    alias(libs.plugins.firebase.app.distribution)
    alias(libs.plugins.navigation.safeargs)

    id("kotlin-parcelize")
//    alias(libs.plugins.androidx.room)
}

val signingProperties = loadProperties("$rootDir/signing.properties")
val debug = "debug"
val release = "release"

android {
    namespace = "com.app.androidcompose"
    compileSdk = 34

//    room {
//        schemaDirectory("$projectDir/schemas")
//    }

    defaultConfig {
        applicationId = "com.app.androidcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters.addAll(listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a"))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create(release) {
            // Remember to edit signing.properties to have the correct info for release build.
            storeFile = file("../config/release.keystore")
            storePassword = signingProperties.getProperty("KEYSTORE_PASSWORD") as String
            keyPassword = signingProperties.getProperty("KEY_PASSWORD") as String
            keyAlias = signingProperties.getProperty("KEY_ALIAS") as String
        }

        getByName(debug) {
            storeFile = file("../config/debug.keystore")
            storePassword = "oQ4mL1jY2uX7wD8q"
            keyAlias = "debug-key-alias"
            keyPassword = "oQ4mL1jY2uX7wD8q"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs[debug]
            buildConfigField("String", "BASE_API_URL", "\"https://dummyjson.com/\"")
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs[release]
            buildConfigField("String", "BASE_API_URL", "\"https://dummyjson.com/\"")

            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("staging") {
            applicationIdSuffix = ".staging"

            firebaseAppDistribution {
                releaseNotes = "Test"
                testers = "full@testers.com"
                groups = "Beta, QA"
            }
        }

        create("prod") {
            applicationIdSuffix = ".prod"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
    lint {
        abortOnError = false
        warningsAsErrors = true
        ignoreTestSources = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.startup.runtime)

    // Data Store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.perf)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.inappmessaging.display.ktx)

    // Lottie
    implementation(libs.lottie)
    implementation(libs.lottie.compose)

    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    // Google Service
//    implementation(libs.play.services.ads)
    implementation(libs.billing)
    implementation(libs.billing.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.integrity)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.review.ktx)
    implementation(libs.installreferrer)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.okhttp.dnsoverhttps)
    implementation(libs.okhttp)

    // Jwt
    implementation(libs.jjwt.jackson)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.coil.svg)

    // Inspect
    debugImplementation(libs.library.chucker)
    releaseImplementation(libs.library.chucker.no.op)

    //Stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)

    // Time
    implementation(libs.kronos.android)
    implementation(libs.joda.time)

    // Logging
    implementation(libs.timber)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.core)
    testImplementation(libs.turbine)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.robolectric)
    testImplementation(libs.room.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
