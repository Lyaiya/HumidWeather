package me.atrin.humidweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV

class HumidWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // OPTIMIZE: 密钥应存储在本地
        const val TOKEN = "GQG7IE2PXti6FpBV"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MMKV.initialize(this)
    }

}