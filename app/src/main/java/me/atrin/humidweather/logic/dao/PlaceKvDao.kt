package me.atrin.humidweather.logic.dao

import com.tencent.mmkv.MMKV
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.network.ServiceCreator
import me.atrin.humidweather.util.adapterPlus

object PlaceKvDao {

    private const val KEY_SAVED_PLACE = "saved_place"
    private const val KEY_SAVED_PLACE_LIST = "saved_place_list"

    private val kv = MMKV.defaultMMKV()

    private val placeAdapter by lazy {
        ServiceCreator.moshi.adapterPlus<Place>()
    }

    fun savePlace(place: Place) =
        kv.encode(KEY_SAVED_PLACE, placeAdapter.toJson(place))

    fun getSavedPlace(): Place? =
        placeAdapter.fromJson(kv.decodeString(KEY_SAVED_PLACE))

    fun isPlaceSaved() = kv.contains(KEY_SAVED_PLACE)

}