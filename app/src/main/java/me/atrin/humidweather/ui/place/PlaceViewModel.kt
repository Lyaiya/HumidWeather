package me.atrin.humidweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.Repository
import me.atrin.humidweather.logic.model.place.Place

class PlaceViewModel : ViewModel() {

    private val searchPlaceNameLiveData by lazy {
        MutableLiveData<String>()
    }

    val searchedPlaceList = ArrayList<Place>()

    // 2. 监听
    val placeLiveData =
        Transformations.switchMap(searchPlaceNameLiveData) { query ->
            Repository.searchPlaces(query)
        }

    // 1. 调用
    fun searchPlaces(query: String) {
        searchPlaceNameLiveData.value = query
    }

    private val _placeNameLiveData by lazy {
        MutableLiveData<String>()
    }

    val placeNameLiveData
        get() = _placeNameLiveData

    fun setPlaceName(placeName: String) {
        _placeNameLiveData.value = placeName
    }

}