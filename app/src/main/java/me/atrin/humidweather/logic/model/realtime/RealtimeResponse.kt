package me.atrin.humidweather.logic.model.realtime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RealtimeResponse(val status: String, val result: Result) {

    @JsonClass(generateAdapter = true)
    data class Result(val realtime: Realtime)

    @JsonClass(generateAdapter = true)
    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @Json(name = "air_quality") val airQuality: AirQuality
    )

    /**
     * 空气质量
     *
     * @property aqi AQI
     * @property pm25 PM25
     * @property pm10 PM10
     * @property description 描述
     */
    @JsonClass(generateAdapter = true)
    data class AirQuality(val aqi: AQI, val pm25: Int, val pm10: Int, val description: Description)

    @JsonClass(generateAdapter = true)
    data class AQI(val chn: Float, val usa: Float)

    @JsonClass(generateAdapter = true)
    data class Description(val chn: String, val usa: String)

}