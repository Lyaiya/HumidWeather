package me.atrin.humidweather.logic.network.service

import me.atrin.humidweather.HumidWeatherApplication
import me.atrin.humidweather.logic.model.response.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${HumidWeatherApplication.TOKEN}&lang=zh_cn")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}