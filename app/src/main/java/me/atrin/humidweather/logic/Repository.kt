package me.atrin.humidweather.logic

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import me.atrin.humidweather.logic.dao.PlaceDao
import me.atrin.humidweather.logic.model.common.ResponseStatus
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.network.HumidWeatherNetwork
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = HumidWeatherNetwork.searchPlaces(query)

        if (placeResponse.status == ResponseStatus.OK) {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                HumidWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                HumidWeatherNetwork.getDailyWeather(lng, lat)
            }
            val deferredHourly = async {
                HumidWeatherNetwork.getHourlyWeather(lng, lat)
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            val hourlyResponse = deferredHourly.await()

            if (realtimeResponse.status == ResponseStatus.OK &&
                dailyResponse.status == ResponseStatus.OK &&
                hourlyResponse.status == ResponseStatus.OK
            ) {
                val weather = Weather(
                    realtimeResponse.result.realtime,
                    dailyResponse.result.daily,
                    hourlyResponse.result.hourly
                )

                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}" +
                                "hourly response status is ${hourlyResponse.status}"
                    )
                )
            }
        }
    }

    fun getSavedPlaceList() = liveData {
        emit(PlaceDao.getSavedPlaceList())
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun deletePlaceByPosition(position: Int) =
        PlaceDao.deletePlaceByPosition(position)

    private fun <T> fire(
        context: CoroutineContext,
        block: suspend () -> Result<T>
    ) = liveData(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

}