package me.atrin.humidweather.ui.activity.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.repository.Repository

class MainViewModel : ViewModel() {

    // 仅用于刷新数据
    private val refreshLiveData by lazy {
        MutableLiveData<Any?>()
    }

    // 2. 监听
    val savedPlaceListLiveData = Transformations.switchMap(refreshLiveData) {
        Repository.getSavedPlaceList()
    }

    val savedPlaceList = ArrayList<Place>()

    fun savedPlaceListSize() = savedPlaceList.size

    fun deletePlaceByPosition(position: Int) = Repository.deletePlaceByPosition(position)

    // 1. 调用
    fun refresh() {
        refreshLiveData.value = refreshLiveData.value
    }

}