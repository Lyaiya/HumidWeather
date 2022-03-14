package me.atrin.humidweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import me.atrin.humidweather.HumidWeatherApplication
import me.atrin.humidweather.logic.model.place.Place

object PlaceDao {

    private const val PREF_TITLE_PLACE = "place"
    private const val KEY_SAVED_PLACE = "saved_place"

    private fun sharedPreferences() =
        HumidWeatherApplication.context.getSharedPreferences(
            PREF_TITLE_PLACE,
            Context.MODE_PRIVATE
        )

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString(KEY_SAVED_PLACE, Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(KEY_SAVED_PLACE, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains(KEY_SAVED_PLACE)

}