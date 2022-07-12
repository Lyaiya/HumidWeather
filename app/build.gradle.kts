plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
}

android {
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        applicationId = "me.atrin.humidweather"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = listOf(
            "-opt-in=kotlin.ExperimentalStdlibApi"
        )
    }

    buildFeatures {
        viewBinding = true
    }

    lint {
        checkDependencies = true
    }
}

kotlin {
    sourceSets.debug {
        kotlin.srcDir("build/generated/ksp/debug/kotlin")
    }
}

dependencies {
    implementation(libs.bundles.androidx.main)
    implementation(libs.bundles.androidx.design)
    implementation(libs.bundles.androidx.lifecycle)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.androidx.test)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.network.main)
    implementation(libs.bundles.network.moshi)
    ksp(libs.bundles.network.moshi.ksp)

    implementation(libs.bundles.design)
    implementation(libs.bundles.longan)

    implementation(libs.bundles.storage)
}