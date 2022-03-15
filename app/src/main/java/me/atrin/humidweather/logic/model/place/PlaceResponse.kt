package me.atrin.humidweather.logic.model.place

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class PlaceResponse(val status: String, val places: List<Place>)

@Parcelize
@JsonClass(generateAdapter = true)
data class Place(
    val name: String,
    val location: Location,
    @Json(name = "formatted_address") val address: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(val lng: String, val lat: String) : Parcelable