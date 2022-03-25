package me.atrin.humidweather.ui.fragment.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.logic.model.place.Location
import me.atrin.humidweather.logic.repository.Repository

class WeatherViewModel : ViewModel() {

    private val locationLiveData by lazy {
        MutableLiveData<Location>()
    }

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val hourlyList = ArrayList<HourlyItem>()

    // 2. 监测到 locationLiveData 发生变动
    val weatherLiveData =
        Transformations.switchMap(locationLiveData) { location ->
            Repository.refreshWeather(location.lng, location.lat)
        }

    // 1. 经纬度数值发生变动
    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

    fun locationIsNotEmpty(): Boolean =
        locationLng.isNotEmpty() && locationLat.isNotEmpty()

}