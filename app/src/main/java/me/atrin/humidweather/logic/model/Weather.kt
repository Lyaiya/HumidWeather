package me.atrin.humidweather.logic.model

import me.atrin.humidweather.logic.model.response.DailyResponse
import me.atrin.humidweather.logic.model.response.RealtimeResponse

data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)