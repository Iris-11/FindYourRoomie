plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.chattutorial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chattutorial"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("io.getstream:stream-chat-android-ui-components:6.4.3")
    implementation("io.getstream:stream-chat-android-ui-components:6.4.3")
    implementation("io.getstream:stream-chat-android-offline:6.4.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("io.coil-kt:coil:2.4.0")
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
}
