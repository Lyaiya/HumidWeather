package me.atrin.humidweather.logic.dao

import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.util.fromJson

object PlaceKvDao {

    private const val KEY_SAVED_PLACE = "saved_place"
    private const val KEY_SAVED_PLACE_LIST = "saved_place_list"

    private val kv = MMKV.defaultMMKV()

    fun savePlace(place: Place) =
        kv.encode(KEY_SAVED_PLACE, Gson().toJson(place))

    fun getSavedPlace(): Place =
        Gson().fromJson<Place>(kv.decodeString(KEY_SAVED_PLACE) ?: "")

    fun isPlaceSaved() = kv.contains(KEY_SAVED_PLACE)

    fun savePlaceToList(placeList: List<Place>) =
        kv.encode(KEY_SAVED_PLACE_LIST, Gson().toJson(placeList))

    // fun getSavedPlaceList(): List<Place> {
    //     val typeOf = object : TypeToken<List<Place>>() {}.type
    //     return Gson().fromJson(kv.decodeStringSet(KEY_SAVED_PLACE_LIST), typeOf)
    //
    // }

    fun isPlaceListEmpty() = kv.contains(KEY_SAVED_PLACE_LIST)

}