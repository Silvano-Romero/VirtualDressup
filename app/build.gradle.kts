plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.virtualdressup2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.virtualdressup2"
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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("it.xabaras.android:recyclerview-swipedecorator:1.4")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-common-ktx:20.4.2")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-messaging:23.4.1")
    implementation("androidx.cardview:cardview:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0-alpha04")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha04")
    // Espresso core library
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0-alpha04")

    // Espresso contrib library (optional, but often useful)
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.0-alpha04")

    // Espresso intents library for testing intents and inter-activity communication
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.4.0")

    // Espresso accessibility library for testing accessibility features
    androidTestImplementation ("androidx.test.espresso:espresso-accessibility:3.4.0")

    // Espresso web library for testing web views
    androidTestImplementation ("androidx.test.espresso:espresso-web:3.4.0")

    // ActivityScenarioRule is part of androidx.test.ext:junit
    androidTestImplementation ("androidx.test.ext:junit:1.2.0-alpha04")

    // UI Automator for testing interactions across app boundaries
    androidTestImplementation ("androidx.test.uiautomator:uiautomator:2.2.0")
}