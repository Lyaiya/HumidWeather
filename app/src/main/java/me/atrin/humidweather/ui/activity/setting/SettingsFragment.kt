package me.atrin.humidweather.ui.activity.setting

import android.os.Bundle
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import com.dylanc.longan.logDebug
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.repository.SettingRepository
import me.atrin.humidweather.util.ResUtil
import rikka.preference.SimpleMenuPreference

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var settingRepository: PreferenceDataStore

    private lateinit var temperatureUnitPref: SimpleMenuPreference

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        preferenceManager.preferenceDataStore = SettingRepository()
        settingRepository = preferenceManager.preferenceDataStore!!

        temperatureUnitPref =
            findPreference(getString(R.string.pref_key_temperature_unit))!!

        temperatureUnitPref.value =
            SettingRepository.getTemperatureUnitString()
        temperatureUnitPref.summary =
            ResUtil.getStringArrayByResId(R.array.setting_temperature_unit_entries)[temperatureUnitPref.value.toInt()]

        temperatureUnitPref.setOnPreferenceChangeListener { _, newValue ->
            val value = newValue as String
            logDebug("onCreatePreferences: TemperatureUnit newValue = $value")
            temperatureUnitPref.summary =
                ResUtil.getStringArrayByResId(R.array.setting_temperature_unit_entries)[value.toInt()]
            true
        }

    }


}