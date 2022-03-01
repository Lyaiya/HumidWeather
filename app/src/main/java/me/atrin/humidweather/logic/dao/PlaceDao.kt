package me.atrin.humidweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import me.atrin.humidweather.HumidWeatherApplication
import me.atrin.humidweather.logic.model.response.Place

object PlaceDao {

    private const val KEY_PLACE = "place"
    private const val KEY_APP_NAME = "humid_weather"

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(KEY_PLACE, Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(KEY_PLACE, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains(KEY_PLACE)

    private fun sharedPreferences() = HumidWeatherApplication.context.getSharedPreferences(
        KEY_APP_NAME,
        Context.MODE_PRIVATE
    )

}