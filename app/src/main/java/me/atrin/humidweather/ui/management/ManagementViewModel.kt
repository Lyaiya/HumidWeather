package me.atrin.humidweather.ui.management

import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.Repository
import me.atrin.humidweather.logic.model.place.Place

class ManagementViewModel : ViewModel() {

    val savedPlaceList = ArrayList<Place>()

    fun clearSavedPlaceList() = Repository.clearSavedPlaceList()

}