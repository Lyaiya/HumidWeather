package me.atrin.humidweather.util

import me.atrin.humidweather.logic.dao.SettingDao
import me.atrin.humidweather.logic.repository.SettingRepository

object WeatherUtil {

    private const val CELSIUS_UNIT = "°C"
    private const val FAHRENHEIT_UNIT = "°F"

    // 摄氏度转为华氏度
    private fun c2t(c: Float) = c * 9 / 5 + 32

    fun getTemperatureText(temperature: Float, withUnit: Boolean): String {
        return when (getTemperatureUnitText()) {
            CELSIUS_UNIT -> temperature.toInt().toString().run {
                if (withUnit) {
                    this + CELSIUS_UNIT
                } else {
                    this
                }
            }
            FAHRENHEIT_UNIT -> c2t(temperature).toInt().toString().run {
                if (withUnit) {
                    this + FAHRENHEIT_UNIT
                } else {
                    this
                }
            }
            else -> ""
        }
    }

    fun getTemperatureUnitText() =
        when (SettingDao.getTemperatureUnitString()) {
            "0" -> CELSIUS_UNIT
            "1" -> FAHRENHEIT_UNIT
            else -> ""
        }

    fun getDailyStep() = SettingRepository.getDailyStepInt()

}