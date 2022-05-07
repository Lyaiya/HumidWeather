package me.atrin.humidweather.util

import me.atrin.humidweather.logic.dao.SettingDao
import me.atrin.humidweather.logic.repository.SettingRepository

private const val CELSIUS_UNIT = "°C"
private const val FAHRENHEIT_UNIT = "°F"

// 摄氏度转为华氏度
private fun c2t(c: Float) = c * 9 / 5 + 32

fun getTemperatureText(temperature: Float, withUnit: Boolean) =
    when (temperatureUnitText) {
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

val temperatureUnitText: String
    get() = when (SettingDao.getTemperatureUnitString()) {
        "0" -> CELSIUS_UNIT
        "1" -> FAHRENHEIT_UNIT
        else -> ""
    }

val dailyStep: Int
    get() = SettingRepository.getDailyStepInt()
