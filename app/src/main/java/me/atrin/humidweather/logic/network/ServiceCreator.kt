package me.atrin.humidweather.logic.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import me.atrin.humidweather.util.addPlus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com"

    private val moshi: Moshi = Moshi.Builder()
        // TODO: 换掉 Date
        .addPlus<Date>(Rfc3339DateJsonAdapter())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun <T> create(serviceClass: Class<T>): T =
        retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}