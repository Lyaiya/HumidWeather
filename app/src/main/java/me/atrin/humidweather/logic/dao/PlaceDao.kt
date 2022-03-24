package me.atrin.humidweather.logic.dao

import com.dylanc.longan.logDebug
import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvString
import com.squareup.moshi.adapter
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.network.ServiceCreator

object PlaceDao : MMKVOwner {

    private var savedPlaceList by mmkvString(
        default = ""
    )

    private val placeListAdapter =
        ServiceCreator.moshi.adapter<List<Place>>()

    fun getSavedPlaceList(): List<Place> {
        return if (savedPlaceList.isEmpty()) {
            emptyList()
        } else {
            placeListAdapter.fromJson(savedPlaceList)!!
        }
    }

    private fun updateSavedPlaceList(newList: List<Place>) {
        savedPlaceList = placeListAdapter.toJson(newList)
    }

    fun clearSavedPlaceList() {
        savedPlaceList = ""
    }

    fun savePlace(newPlace: Place) {
        val tempSavedPlaceList = getSavedPlaceList().toMutableList()

        if (tempSavedPlaceList.isEmpty()) {
            logDebug("savePlace: oldSavedPlaceList is empty")
        } else {
            tempSavedPlaceList.forEachIndexed { index, place ->
                logDebug("savePlace: oldSavedPlaceList #$index = $place")
            }
        }

        logDebug("savePlace: newSavedPlace = $newPlace")

        if (tempSavedPlaceList.contains(newPlace)) {
            logDebug("savePlace: oldSavedPlaceList is contains newPlace, skip savePlace")
        } else {
            tempSavedPlaceList.add(newPlace)
            updateSavedPlaceList(tempSavedPlaceList)
            tempSavedPlaceList.forEachIndexed { index, place ->
                logDebug("savePlace: newSavedPlaceList #$index = $place")
            }
        }
    }

}