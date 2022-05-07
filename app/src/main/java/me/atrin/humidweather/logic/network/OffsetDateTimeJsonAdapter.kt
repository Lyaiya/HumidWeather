package me.atrin.humidweather.logic.network

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime

class OffsetDateTimeJsonAdapter {

    @FromJson
    fun fromJson(offsetDateTimeJson: String): OffsetDateTime =
        OffsetDateTime.parse(offsetDateTimeJson)

    @ToJson
    fun toJson(offsetDateTime: OffsetDateTime) = offsetDateTime.toString()

}