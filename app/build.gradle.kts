plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = rootProject.extra["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "dev.hnatiuk.uno_score"
        minSdk = rootProject.extra["minSdkVersion"] as Int
        targetSdk = rootProject.extra["targetSdkVersion"] as Int

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

apply(from = "../dependencies.gradle.kts")

val String.asDependency: String
    get() = project.extra[this] as String

dependencies {
    implementation(project(":core"))

    implementation("appCompat".asDependency)
    implementation("coreKtx".asDependency)
    implementation("fragmentKtx".asDependency)
    implementation("material".asDependency)
    implementation("cicerone".asDependency)
    implementation("adapterDelegatesLayoutContainer".asDependency)
    implementation("adapterDelegatesViewBinding".asDependency)
    implementation("hiltAndroid".asDependency)
    kapt("hiltCompiler".asDependency)
}