package me.atrin.humidweather.logic.model.realtime

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    // 空气质量
    data class AirQuality(val aqi: AQI, val pm25: Int, val pm10: Int, val description: Description)

    data class AQI(val chn: Float, val usa: Float)

    data class Description(val chn: String, val usa: String)

}