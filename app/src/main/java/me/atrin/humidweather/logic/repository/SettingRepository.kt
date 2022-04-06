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

        fun getDailyStepInt() = SettingDao.getDailyStepInt()

        fun setDailyStepInt(int: Int) = SettingDao.setDailyStepInt(int)
    }

    private val prefKeyTemperatureUnit =
        ResUtil.getStringByResId(R.string.pref_key_temperature_unit)

    private val prefKeyDailyStep =
        ResUtil.getStringByResId(R.string.pref_key_daily_step)

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

    override fun putInt(key: String?, value: Int) {
        logDebug("putInt: start")

        if (key == null) {
            return
        }

        when (key) {
            prefKeyDailyStep -> setDailyStepInt(value)
        }
    }

    override fun getInt(key: String?, defValue: Int): Int {
        logDebug("getString: start")
        if (key == null) {
            return 0
        }

        return when (key) {
            prefKeyDailyStep -> getDailyStepInt()
            else -> defValue
        }
    }

}