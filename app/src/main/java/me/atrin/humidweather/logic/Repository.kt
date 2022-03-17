package me.atrin.humidweather.logic

import androidx.lifecycle.liveData
import com.dylanc.longan.logDebug
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

    fun getSavedPlaceSet(): Set<Place> {
        logDebug("getSavedPlaceSetLiveData: start")

        val mutableSet = mutableSetOf<Place>()

        PlaceDao.savedPlaceSet.forEach { savedPlace ->
            PlaceDao.adapter.fromJson(savedPlace)?.let { place ->
                logDebug("getSavedPlaceSetLiveData: place = $place")
                mutableSet.add(place)
            }
        }

        logDebug("getSavedPlaceSetLiveData: end")

        return mutableSet.toSet()
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun deleteAllSavedPlaces() = PlaceDao.deleteAllSavedPlaces()

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