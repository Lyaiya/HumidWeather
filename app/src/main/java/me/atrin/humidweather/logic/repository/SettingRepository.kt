package me.atrin.humidweather.logic.repository

import androidx.preference.PreferenceDataStore
import com.dylanc.longan.logDebug
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.dao.SettingDao
import me.atrin.humidweather.util.ResUtil

class SettingRepository : PreferenceDataStore() {

    companion object {
        fun getTemperatureUnitString() = SettingDao.getTemperatureUnitString()

        fun setTemperatureUnitString(string: String) = SettingDao.setTemperatureUnitString(string)
    }

    private val prefKeyTemperatureUnit =
        ResUtil.getStringByResId(R.string.pref_key_temperature_unit)

    override fun putString(key: String?, value: String?) {
        logDebug("putString: start")
        if (key == null || value == null) {
            return
        }

        when (key) {
            prefKeyTemperatureUnit -> setTemperatureUnitString(value)
        }
    }

    override fun getString(key: String?, defValue: String?): String {
        logDebug("getString: start")
        if (key == null || defValue == null) {
            return ""
        }

        return when (key) {
            prefKeyTemperatureUnit -> getTemperatureUnitString()
            else -> defValue
        }
    }

}