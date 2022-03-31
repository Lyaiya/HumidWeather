package me.atrin.humidweather.logic.dao

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvString
import com.tencent.mmkv.MMKV

object SettingDao : MMKVOwner {

    override val kv: MMKV
        get() = MMKV.mmkvWithID("setting.default")

    private var temperatureUnit by mmkvString("0")

    fun getTemperatureUnitString() = temperatureUnit

    fun setTemperatureUnitString(string: String) {
        temperatureUnit = string
    }

}