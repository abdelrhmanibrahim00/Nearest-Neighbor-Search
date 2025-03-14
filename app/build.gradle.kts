plugins {
    id("com.android.application") // For Android application module
    id("org.jetbrains.kotlin.android") // For Kotlin Android support
    id("kotlin-kapt") // For annotation processing, required for Room and other libraries
}

android {
    namespace = "com.example.signalstrengthapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.signalstrengthapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true // Enable view binding for all activities/fragments
    }
}

dependencies {
    // Material Design Library
    implementation("com.google.android.material:material:1.9.0")

    // Android Core Libraries
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.6.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.activity:activity-ktx:1.7.2")

    // Room Database
    implementation("androidx.room:room-runtime:2.7.0-alpha10")
    implementation("androidx.room:room-ktx:2.7.0-alpha10")
    kapt("androidx.room:room-compiler:2.7.0-alpha10") // Use kapt for annotation processing

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // Networking with Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //snack
    implementation("com.google.android.material:material:1.9.0")

}
