package me.atrin.humidweather.logic

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.atrin.humidweather.logic.network.HumidWeatherNetwork

object Repository {
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = HumidWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}