plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)


    alias(libs.plugins.ksp)




    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.projettdm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projettdm"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.protolite.well.known.types)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.runtime)
    implementation(libs.play.services.location)
    implementation(libs.firebase.messaging.ktx)


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
//    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Google Auth Dependency
    implementation(libs.play.services.auth)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // System UI Controller
    implementation(libs.accompanist.systemuicontroller)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Pager and Indicators - Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    //Map
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)

    //coil
    implementation(libs.coil.compose)

    annotationProcessor(libs.compiler)
    implementation (libs.androidx.room.ktx)
    testImplementation (libs.androidx.room.testing)

    //jwt-decoder
    implementation(libs.jwtdecode)

    //QR code
    implementation(libs.zxing)
    implementation(libs.zxing2)

<<<<<<< main
    //push notifications
    implementation(libs.firebase.bom)

    implementation(libs.firebase.messaging)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.accompanist.permissions)

=======
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
>>>>>>> notifications-backup
}