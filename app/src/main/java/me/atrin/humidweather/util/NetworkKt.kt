package me.atrin.humidweather.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.Builder.addPlus(jsonAdapter: JsonAdapter<T>)
        : Moshi.Builder = add(T::class.java, jsonAdapter)
