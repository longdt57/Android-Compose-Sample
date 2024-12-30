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
    alias(libs.plugins.kotlinx.kover)

    id("kotlin-parcelize")
//    alias(libs.plugins.androidx.room)
}

val signingProperties = loadProperties("$rootDir/signing.properties")
val debug = "debug"
val release = "release"

android {
    namespace = "com.app.androidcompose"
    compileSdk = libs.versions.compileSdk.get().toInt()

//    room {
//        schemaDirectory("$projectDir/schemas")
//    }

    defaultConfig {
        applicationId = "com.app.androidcompose"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        ndk {
            abiFilters.addAll(listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a"))
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BASE_API_URL", "\"https://dummyjson.com/\"")
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
            isDefault = true
            isMinifyEnabled = false
            signingConfig = signingConfigs[debug]
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs[release]
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("staging") {
            isDefault = true
            applicationIdSuffix = ".staging"
        }

        create("prod") {
            applicationIdSuffix = ".prod"
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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":analytics"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Lifecycle
    implementation(libs.bundles.androidx.lifecycle)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.startup.runtime)

    // Data Store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Lottie
    implementation(libs.lottie)
    implementation(libs.lottie.compose)

    // Coroutines
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)

    // Google Service
    implementation(libs.bundles.google.services)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.bundles.networking)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Room
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // Coil
    implementation(libs.bundles.coil)

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

    testImplementation(libs.bundles.test)
    testImplementation(libs.androidx.ui.test.junit4)
    testImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.work.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}

dependencies {
    kover(project(":data"))
    kover(project(":domain"))
    kover(project(":analytics"))
}

kover {
    reports {
        filters {
            includes {
                classes("*ViewModel")
                classes("*UseCase")
                classes("*Mapper")
                classes("*MapperImpl")
                classes("*Repository")
                classes("*RepositoryImpl")
                classes("*Util")
                classes("*Formatter")
                classes("*FormatterImpl")
                classes("*Converter")
                classes("*ConverterImpl")
            }
            excludes {
                classes("_")
            }
        }
    }
}
