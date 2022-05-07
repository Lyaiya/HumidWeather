package me.atrin.humidweather

import android.app.Application
import com.dylanc.longan.initLogger

class HumidWeatherApplication : Application() {

    companion object {
        // OPTIMIZE: 密钥应存储在本地
        const val TOKEN = "GQG7IE2PXti6FpBV"
    }

    init {
        initLogger(BuildConfig.DEBUG)
    }

}