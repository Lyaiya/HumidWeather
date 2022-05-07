package me.atrin.humidweather.logic.model.hourly

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.atrin.humidweather.logic.model.common.Sky
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class HourlyResponse(val status: String, val result: Result) {

    @JsonClass(generateAdapter = true)
    data class Result(val hourly: Hourly)

    @JsonClass(generateAdapter = true)
    data class Hourly(
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
        val description: String
    )

    /**
     * 天气状况
     *
     * @property value 数值
     * @property dateTime 时间
     */
    @JsonClass(generateAdapter = true)
    data class Skycon(val value: String, @Json(name = "datetime") val dateTime: OffsetDateTime)

    /**
     * 温度
     *
     * @property value 数值
     */
    @JsonClass(generateAdapter = true)
    data class Temperature(val value: Float)

}

data class HourlyItem(val dateTime: OffsetDateTime, val skyIcon: Sky, val temperature: Float)