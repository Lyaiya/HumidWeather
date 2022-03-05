package me.atrin.humidweather.logic.model.hourly

import java.util.*

data class HourlyResponse(val status: String, val result: Result) {

    data class Result(val hourly: Hourly)

    data class Hourly(
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
        val description: String
    )

    // 天气状况
    data class Skycon(
        val value: Float,
        val datetime: Date
    )

    // 温度
    data class Temperature(
        val value: Float
    )


}