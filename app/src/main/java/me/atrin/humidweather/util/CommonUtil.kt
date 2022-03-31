package me.atrin.humidweather.util

import androidx.core.os.LocaleListCompat
import me.atrin.humidweather.BuildConfig
import java.util.*

object CommonUtil {

    fun getLanguage() =
        if (LocaleListCompat.getDefault().get(0).language == Locale.CHINA.language) {
            "zh_CN"
        } else {
            "en_US"
        }

    fun getVersionName() = BuildConfig.VERSION_NAME

}