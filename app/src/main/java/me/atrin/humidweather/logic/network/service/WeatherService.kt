package me.atrin.humidweather.logic.network.service

import me.atrin.humidweather.HumidWeatherApplication
import me.atrin.humidweather.logic.model.daily.DailyResponse
import me.atrin.humidweather.logic.model.hourly.HourlyResponse
import me.atrin.humidweather.logic.model.realtime.RealtimeResponse
import me.atrin.humidweather.util.CommonUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2.5/${HumidWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("lang") lang: String = CommonUtil.getLanguage()
    ): Call<RealtimeResponse>

    @GET("v2.5/${HumidWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("lang") lang: String = CommonUtil.getLanguage()
    ): Call<DailyResponse>

    @GET("v2.5/${HumidWeatherApplication.TOKEN}/{lng},{lat}/hourly.json")
    fun getHourlyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String,
        @Query("lang") lang: String = CommonUtil.getLanguage()
    ): Call<HourlyResponse>

}