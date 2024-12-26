plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.alergin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.alergin"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
    }
}



dependencies {
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.litert.support.api)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("org.json:json:20210307")
    implementation ("com.google.zxing:core:3.4.1")
    // Core library
    implementation ("androidx.core:core-ktx:1.6.0")// Asegúrate de usar la última versión

    // AppCompat for backward-compatible versions of Android components
    implementation ("androidx.appcompat:appcompat:1.3.1")

    // Material Design Components
    implementation ("com.google.android.material:material:1.4.0")

    // Activity KTX for Kotlin extensions
    implementation ("androidx.activity:activity-ktx:1.3.1")

    // ConstraintLayout for complex layouts
    implementation ("androidx.constraintlayout:constraintlayout:2.1.0")

    // RecyclerView for efficient display of lists
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    // JUnit for unit testing
    testImplementation ("junit:junit:4.13.2")

    // AndroidX Test - JUnit
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

    // Espresso for UI testing
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")


}