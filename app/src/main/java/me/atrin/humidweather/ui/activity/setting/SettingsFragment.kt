package me.atrin.humidweather.ui.activity.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import com.dylanc.longan.logDebug
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.repository.SettingRepository
import me.atrin.humidweather.util.CommonUtil
import me.atrin.humidweather.util.ResUtil
import rikka.preference.SimpleMenuPreference

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var settingRepository: PreferenceDataStore

    private lateinit var temperatureUnitPref: SimpleMenuPreference
    private lateinit var versionPref: Preference

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        initPrefDataStore()
        defineView()

        initTemperatureUnitPref()
        initVersionPref()
    }

    private fun initPrefDataStore() {
        preferenceManager.preferenceDataStore = SettingRepository()
        settingRepository = preferenceManager.preferenceDataStore!!
    }

    private fun defineView() {
        temperatureUnitPref =
            findPreference(getString(R.string.pref_key_temperature_unit))!!
        versionPref = findPreference(getString(R.string.pref_key_version))!!
    }

    private fun initTemperatureUnitPref() {
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

    private fun initVersionPref() {
        versionPref.summary = CommonUtil.getVersionName()
    }

}