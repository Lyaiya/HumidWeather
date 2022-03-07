package me.atrin.humidweather.logic.model.hourly

import me.atrin.humidweather.logic.model.common.Sky
import java.util.*

data class HourlyResponse(val status: String, val result: Result) {

    data class Result(val hourly: Hourly)

    data class Hourly(
        val skycon: List<Skycon>,
        val temperature: List<Temperature>,
        val description: String
    )

    /**
     * 天气状况
     *
     * @property value 数值
     * @property datetime 时间
     */
    data class Skycon(val value: String, val datetime: Date)

    /**
     * 温度
     *
     * @property value 数值
     */
    data class Temperature(val value: Float)

}

data class HourlyItem(val date: String, val skyIcon: Sky, val temperature: Float)