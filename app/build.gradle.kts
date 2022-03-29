plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "me.atrin.humidweather"
        minSdk = 24
        targetSdk = 32
        versionCode = 4
        versionName = "1.2.2"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    val lifecycleVersion = "2.4.1"
    val retrofitVersion = "2.9.0"
    val navVersion = "2.4.1"
    val longanVersion = "1.0.5"
    val moshiVersion = "1.13.0"

    implementation("com.google.android.material:material:1.5.0")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.appcompat:appcompat:1.4.1")

    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.transition:transition-ktx:1.4.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // GitHub: https://github.com/square/retrofit
    // Docs: https://square.github.io/retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // GitHub: https://github.com/square/okio
    // Docs: https://square.github.io/okio
    implementation("com.squareup.okio:okio:3.0.0")

    // GitHub: https://github.com/square/okhttp
    // Docs: https://square.github.io/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // GitHub/Docs: https://github.com/square/moshi
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")

    // GitHub: https://github.com/DylanCaiCoding/ViewBindingKTX
    // Docs: https://dylancaicoding.github.io/ViewBindingKTX
    implementation("com.github.DylanCaiCoding.ViewBindingKTX:viewbinding-base:2.0.2")

    // GitHub/Docs: https://github.com/drakeet/MultiType
    implementation("com.drakeet.multitype:multitype:4.3.0")

    // GitHub/Docs: https://github.com/Zackratos/UltimateBarX
    implementation("com.gitee.zackratos:UltimateBarX:0.8.0")

    // GitHub: https://github.com/DylanCaiCoding/ActivityResultLauncher
    // Docs: https://dylancaicoding.github.io/ActivityResultLauncher
    implementation("com.github.DylanCaiCoding:ActivityResultLauncher:1.1.2")

    // GitHub: https://github.com/DylanCaiCoding/Longan
    // Docs: https://dylancaicoding.github.io/Longan
    implementation("com.github.DylanCaiCoding.Longan:longan:$longanVersion")
    implementation("com.github.DylanCaiCoding.Longan:longan-design:$longanVersion")

    // GitHub: https://github.com/RikkaApps/RikkaX
    implementation("dev.rikka.rikkax.preference:simplemenu-preference:1.0.3")

    // GitHub: https://github.com/Tencent/MMKV
    // Docs: https://github.com/Tencent/MMKV/wiki/android_tutorial_cn
    // GitHub: https://github.com/DylanCaiCoding/MMKV-KTX
    // Docs: https://github.com/DylanCaiCoding/MMKV-KTX/blob/master/README_CN.md
    implementation("com.github.DylanCaiCoding:MMKV-KTX:1.2.11")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}