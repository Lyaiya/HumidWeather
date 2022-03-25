package me.atrin.humidweather.logic.repository

import androidx.preference.PreferenceDataStore
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.dao.SettingDao
import me.atrin.humidweather.util.ResUtil

class SettingRepository : PreferenceDataStore() {

    override fun putString(key: String?, value: String?) {
        if (key == null || value == null) {
            return
        }

        when (key) {
            ResUtil.getStringByResId(R.string.pref_key_temperature_unit) -> {
                SettingDao.temperatureUnit = value
            }
        }
    }

    override fun getString(key: String?, defValue: String?): String {
        if (key == null || defValue == null) {
            return ""
        }

        return when (key) {
            ResUtil.getStringByResId(R.string.pref_key_temperature_unit) -> {
                SettingDao.temperatureUnit
            }
            else -> defValue
        }
    }

}