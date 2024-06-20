plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.aslansoft.caffesist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aslansoft.caffesist"
        minSdk = 29
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //Firebase
    //noinspection UseTomlInstead
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-analytics")
    // Use this dependency to bundle the model with your app
    //noinspection UseTomlInstead
    implementation("com.google.mlkit:barcode-scanning:17.2.0")
    // Use this dependency to use the dynamically downloaded model in Google Play Services
    //noinspection UseTomlInstead
    implementation ("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.0")
    // The following line is optional, as the core library is included indirectly by camera-camera2
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-core:1.4.0-beta01")
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-camera2:1.4.0-beta01")
    // If you want to additionally use the CameraX Lifecycle library
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-lifecycle:1.4.0-beta01")
    // If you want to additionally use the CameraX VideoCapture library
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-video:1.4.0-beta01")
    // If you want to additionally use the CameraX View class
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-view:1.4.0-beta01")
    // If you want to additionally add CameraX ML Kit Vision Integration
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-mlkit-vision:1.4.0-beta01")
    // If you want to additionally use the CameraX Extensions library
    //noinspection UseTomlInstead
    implementation ("androidx.camera:camera-extensions:1.4.0-beta01")
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //Firestore
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-firestore")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    //firestore
    //noinspection UseTomlInstead
    implementation("com.google.firebase:firebase-firestore")
    //Painter
    //noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:2.6.0")
    //RememberSystemUiController
    //noinspection UseTomlInstead
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.35.1-alpha")




}