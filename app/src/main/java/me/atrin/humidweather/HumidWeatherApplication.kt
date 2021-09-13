package me.atrin.humidweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class HumidWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "GQG7IE2PXti6FpBV"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}