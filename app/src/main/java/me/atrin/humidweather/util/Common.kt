package me.atrin.humidweather.util

import androidx.core.os.LocaleListCompat
import me.atrin.humidweather.BuildConfig
import java.util.*

val weatherLanguage: String
    get() = if (LocaleListCompat.getDefault()[0]?.language == Locale.SIMPLIFIED_CHINESE.language) {
        Locale.SIMPLIFIED_CHINESE.toString()
    } else {
        Locale.US.toString()
    }

val appVersionName: String
    get() = BuildConfig.VERSION_NAME