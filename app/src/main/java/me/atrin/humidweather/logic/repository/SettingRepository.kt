package me.atrin.humidweather.logic.repository

import androidx.preference.PreferenceDataStore
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.dao.SettingDao
import me.atrin.humidweather.util.ResUtil

class SettingRepository : PreferenceDataStore() {

    companion object {
        fun getTemperatureUnitString() =
            SettingDao.getTemperatureUnitString()

        fun setTemperatureUnitString(string: String) =
            SettingDao.setTemperatureUnitString(string)
    }

    override fun putString(key: String?, value: String?) {
        if (key == null || value == null) {
            return
        }

        when (key) {
            ResUtil.getStringByResId(R.string.pref_key_temperature_unit) -> {
                SettingDao.setTemperatureUnitString(value)
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String {
        if (key == null || defValue == null) {
            return ""
        }

        return when (key) {
            ResUtil.getStringByResId(R.string.pref_key_temperature_unit) -> {
                SettingDao.getTemperatureUnitString()
            }
            else -> defValue
        }
    }

}