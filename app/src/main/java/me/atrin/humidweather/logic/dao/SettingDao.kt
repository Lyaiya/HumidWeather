package me.atrin.humidweather.logic.dao

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvInt
import com.dylanc.mmkv.mmkvString
import com.tencent.mmkv.MMKV

object SettingDao : MMKVOwner {

    override val kv: MMKV
        get() = MMKV.mmkvWithID("setting.default")

    private var temperatureUnit by mmkvString("0")

    private var dailyStep by mmkvInt(5)

    fun getTemperatureUnitString() = temperatureUnit

    fun setTemperatureUnitString(string: String) {
        temperatureUnit = string
    }

    fun getDailyStepInt() = dailyStep

    fun setDailyStepInt(int: Int) {
        dailyStep = int
    }

}