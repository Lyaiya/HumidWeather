package me.atrin.humidweather.logic.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com"

    val moshi: Moshi = Moshi.Builder()
        .add(OffsetDateTimeJsonAdapter())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

}