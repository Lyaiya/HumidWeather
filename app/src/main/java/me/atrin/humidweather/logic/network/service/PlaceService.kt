package me.atrin.humidweather.logic.network.service

import me.atrin.humidweather.HumidWeatherApplication
import me.atrin.humidweather.logic.model.place.PlaceResponse
import me.atrin.humidweather.util.weatherLanguage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${HumidWeatherApplication.TOKEN}")
    fun searchPlaces(
        @Query("query") query: String,
        @Query("lang") lang: String = weatherLanguage
    ): Call<PlaceResponse>

}