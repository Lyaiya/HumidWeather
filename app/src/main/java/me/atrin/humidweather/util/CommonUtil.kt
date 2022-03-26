package me.atrin.humidweather.util

import androidx.core.os.LocaleListCompat
import me.atrin.humidweather.BuildConfig
import java.util.*

object CommonUtil {

    fun getLanguage(): String {
        return if (LocaleListCompat.getDefault().get(0).language
            == Locale.CHINA.language
        ) {
            "zh_CN"
        } else {
            "en_US"
        }
    }

    fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

}