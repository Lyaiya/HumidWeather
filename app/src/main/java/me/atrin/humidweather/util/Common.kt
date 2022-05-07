package me.atrin.humidweather.util

import androidx.core.os.LocaleListCompat
import me.atrin.humidweather.BuildConfig
import java.util.*

val weatherLanguage: String
    get() = if (LocaleListCompat.getDefault().get(0).language == Locale.CHINA.language) {
        "zh_CN"
    } else {
        "en_US"
    }

val appVersionName: String
    get() = BuildConfig.VERSION_NAME