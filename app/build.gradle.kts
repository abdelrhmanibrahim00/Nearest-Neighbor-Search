plugins {
    id("com.android.application") // Only include once
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // For Room and other annotation processing
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
        viewBinding = true
    }
}

dependencies {
    implementation("com.google.android.material:material:1.9.0")

    // Core Libraries
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.4.0")

    // Room Dependencies
    implementation("androidx.room:room-runtime:2.7.0-alpha10")
    implementation("androidx.room:room-ktx:2.7.0-alpha10")
    implementation(libs.androidx.activity)
    annotationProcessor("androidx.room:room-compiler:2.7.0-alpha10")

    // Navigation and UI Dependencies
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.annotation:annotation:1.6.0")

    // Retrofit for Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

}
