package me.atrin.humidweather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dylanc.longan.logDebug
import me.atrin.humidweather.logic.Repository
import me.atrin.humidweather.logic.model.place.Place

class MainViewModel : ViewModel() {

    private val refreshLiveData by lazy {
        MutableLiveData<Any?>()
    }

    // 2. 监听
    val savedPlacesLiveData = Transformations.switchMap(refreshLiveData) {
        MutableLiveData<Set<Place>>().also {
            it.value = Repository.getSavedPlaceSet()
        }
    }

    val savedPlaceList = ArrayList<Place>()

    fun savePlace(place: Place) = Repository.savePlace(place)

    // 1. 调用
    fun refresh() {
        logDebug("refresh: start")
        refreshLiveData.value = refreshLiveData.value
    }

}