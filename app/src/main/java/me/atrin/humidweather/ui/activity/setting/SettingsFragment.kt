package me.atrin.humidweather.ui.activity.setting

import android.os.Bundle
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceFragmentCompat
import com.dylanc.longan.logDebug
import me.atrin.humidweather.R
import me.atrin.humidweather.logic.repository.SettingRepository
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

        temperatureUnitPref.setOnPreferenceChangeListener { preference, newValue ->
            logDebug("onCreatePreferences: TemperatureUnit newValue = $newValue")
            true
        }

    }


}