package me.atrin.humidweather.logic.model.common

import me.atrin.humidweather.logic.model.daily.DailyResponse
import me.atrin.humidweather.logic.model.hourly.HourlyResponse
import me.atrin.humidweather.logic.model.realtime.RealtimeResponse

data class Weather(
    val realtime: RealtimeResponse.Realtime,
    val daily: DailyResponse.Daily,
    val hourly: HourlyResponse.Hourly
)