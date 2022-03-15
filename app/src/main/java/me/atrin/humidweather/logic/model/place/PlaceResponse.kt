package me.atrin.humidweather.logic.model.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceResponse(val status: String, val places: List<Place>)

@JsonClass(generateAdapter = true)
data class Place(
    val name: String,
    val location: Location,
    @Json(name = "formatted_address") val address: String
)

@JsonClass(generateAdapter = true)
data class Location(val lng: String, val lat: String)