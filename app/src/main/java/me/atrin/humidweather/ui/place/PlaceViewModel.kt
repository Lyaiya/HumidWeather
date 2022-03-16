package me.atrin.humidweather.ui.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.Repository
import me.atrin.humidweather.logic.model.place.Place

class PlaceViewModel : ViewModel() {

    private val searchPlaceLiveData by lazy {
        MutableLiveData<String>()
    }

    val placeList = ArrayList<Place>()

    val placeLiveData =
        Transformations.switchMap(searchPlaceLiveData) { query ->
            Repository.searchPlaces(query)
        }


    fun searchPlaces(query: String) {
        searchPlaceLiveData.value = query
    }


    private val _placeNameLiveData by lazy {
        MutableLiveData<String>()
    }

    val placeNameLiveData: LiveData<String>
        get() = _placeNameLiveData

    fun setPlaceName(placeName: String) {
        _placeNameLiveData.value = placeName
    }

    fun clearPlaceName() {
        _placeNameLiveData.value = ""
    }

}