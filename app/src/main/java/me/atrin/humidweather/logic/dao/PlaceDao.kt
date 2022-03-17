package me.atrin.humidweather.logic.dao

import com.dylanc.longan.logInfo
import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvStringSet
import com.squareup.moshi.adapter
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.network.ServiceCreator

object PlaceDao : MMKVOwner {

    val adapter = ServiceCreator.moshi.adapter<Place>()

    var savedPlaceSet by mmkvStringSet(
        default = emptySet()
    )

    fun savePlace(newPlace: Place) {
        val newPlaceText = adapter.toJson(newPlace)

        logInfo("savePlace: newSavedPlace: $newPlaceText")

        savedPlaceSet.forEachIndexed { index, place ->
            logInfo("savePlace: oldSavedPlaces #$index: $place")
        }

        // FIXME: 顺序问题
        savedPlaceSet = savedPlaceSet + newPlaceText

        savedPlaceSet.forEachIndexed { index, place ->
            logInfo("savePlace: newSavedPlaces #$index: $place")
        }
    }

    fun deleteAllSavedPlaces() = kv.clearAll()

}