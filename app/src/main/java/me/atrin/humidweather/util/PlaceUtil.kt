package me.atrin.humidweather.util

import androidx.core.os.LocaleListCompat
import com.dylanc.longan.logDebug
import java.util.*

object PlaceUtil {

    val LANGUAGE = if (LocaleListCompat.getDefault()
            .get(0).language == Locale.CHINA.language
    ) {
        "zh_CN"
    } else {
        "en_US"
    }.also {
        logDebug("language = $it")
    }

}