package me.atrin.humidweather.util

import me.atrin.humidweather.logic.dao.SettingDao

object WeatherUtil {

    private const val CELSIUS_UNIT = "°C"
    private const val FAHRENHEIT_UNIT = "°F"

    // 摄氏度转为华氏度
    fun c2t(c: Float) = c * 9 / 5 + 32

    fun getTemperatureText(temperature: Float) =
        when (getTemperatureUnitText()) {
            CELSIUS_UNIT -> "${temperature.toInt()}$CELSIUS_UNIT"
            FAHRENHEIT_UNIT -> "${c2t(temperature).toInt()}$FAHRENHEIT_UNIT"
            else -> ""
        }

    fun getTemperatureUnitText() =
        when (SettingDao.getTemperatureUnitString()) {
            "0" -> CELSIUS_UNIT
            "1" -> FAHRENHEIT_UNIT
            else -> ""
        }

}