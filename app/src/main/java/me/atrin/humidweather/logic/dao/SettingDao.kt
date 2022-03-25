package me.atrin.humidweather.logic.dao

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvString
import com.tencent.mmkv.MMKV
import me.atrin.humidweather.R
import me.atrin.humidweather.util.ResUtil

object SettingDao : MMKVOwner {

    override val kv: MMKV
        get() = MMKV.mmkvWithID("setting.default")

    private var temperatureUnit by mmkvString(
        default = ResUtil.getStringByResId(R.string.pref_default_value_temperature_unit)
    )

    fun getTemperatureUnitString() = temperatureUnit

    fun setTemperatureUnitString(string: String) {
        temperatureUnit = string
    }

}