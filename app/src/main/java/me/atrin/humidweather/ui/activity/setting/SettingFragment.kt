package me.atrin.humidweather.ui.activity.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.repository.SettingRepository
import me.atrin.humidweather.util.appVersionName
import rikka.preference.SimpleMenuPreference

class SettingFragment : PreferenceFragmentCompat() {

    private lateinit var settingRepository: PreferenceDataStore

    private lateinit var temperatureUnitPref: SimpleMenuPreference
    private lateinit var dailyStepPref: SeekBarPreference
    private lateinit var versionPref: Preference

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        initPrefDataStore()
        defineView()

        initTemperatureUnitPref()
        initDailyStepPref()
        initVersionPref()
    }

    private fun initPrefDataStore() {
        settingRepository = SettingRepository()
        preferenceManager.preferenceDataStore = settingRepository
    }

    private fun defineView() {
        temperatureUnitPref = findPreference(getString(R.string.pref_key_temperature_unit))!!
        dailyStepPref = findPreference(getString(R.string.pref_key_daily_step))!!
        versionPref = findPreference(getString(R.string.pref_key_version))!!
    }

    private fun initTemperatureUnitPref() {
        temperatureUnitPref.value = SettingRepository.getTemperatureUnitString()
    }

    private fun initDailyStepPref() {
        dailyStepPref.value = SettingRepository.getDailyStepInt()
        dailyStepPref.summary = dailyStepPref.value.toString()
        dailyStepPref.setOnPreferenceChangeListener { preference, newValue ->
            preference.summary = newValue.toString()
            true
        }
    }

    private fun initVersionPref() {
        versionPref.summary = appVersionName
    }

}