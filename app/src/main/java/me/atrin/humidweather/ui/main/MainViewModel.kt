package me.atrin.humidweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.Repository
import me.atrin.humidweather.logic.model.place.Location
import me.atrin.humidweather.logic.model.place.Place

class MainViewModel : ViewModel() {

    // private val _savedPlaceLiveData by lazy {
    //     MutableLiveData<Place>()
    // }
    //
    // val savedPlaceLiveData =
    //     Transformations.switchMap(_savedPlaceLiveData) {
    //         liveData<Place> { getSavedPlace() }
    //     }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

    private val _placesCountLiveData by lazy {
        MutableLiveData<Int>().also {
            it.value = getPlacesCount()
        }
    }

    val placesCountLiveData: LiveData<Int>
        get() = _placesCountLiveData

    private fun getPlacesCount() = 3

    val places by lazy {
        mutableListOf<Place>().also {
            it.add(Place("珠海市", Location("113.57", "22.27"), ""))
            it.add(Place("广州市", Location("113.27", "23.13"), ""))
        }
    }

}