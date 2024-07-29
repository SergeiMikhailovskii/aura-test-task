plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("app.cash.sqldelight") version "2.0.2"
}

android {
    namespace = "com.mikhailovskii.aura.test.task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mikhailovskii.aura.test.task"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("io.insert-koin:koin-android:3.5.6")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("app.cash.sqldelight:android-driver:2.0.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

sqldelight {
    databases {
        create("AuraTestTaskDB") {
            packageName.set("com.mikhailovskii.aura.test.task")
        }
    }
}
