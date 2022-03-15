package me.atrin.humidweather.logic.dao

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvParcelable
import me.atrin.humidweather.logic.model.place.Location
import me.atrin.humidweather.logic.model.place.Place

object PlaceKvDao : MMKVOwner {

    var savedPlace by mmkvParcelable(
        default = Place(
            "",
            Location("", ""),
            ""
        )
    )

    fun savePlace(place: Place) {
        savedPlace = place
    }

    fun isPlaceSaved(): Boolean = !(savedPlace.name.isEmpty()
            || savedPlace.location.lng.isEmpty()
            || savedPlace.location.lat.isEmpty()
            || savedPlace.address.isEmpty())

}