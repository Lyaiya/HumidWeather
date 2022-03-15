package me.atrin.humidweather.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.adapterPlus(): JsonAdapter<T> =
    adapter(T::class.java)