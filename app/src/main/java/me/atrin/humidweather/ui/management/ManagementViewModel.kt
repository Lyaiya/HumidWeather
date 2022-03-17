package me.atrin.humidweather.ui.management

import androidx.lifecycle.ViewModel
import me.atrin.humidweather.logic.Repository

class ManagementViewModel : ViewModel() {

    fun deleteAllSavedPlaces() = Repository.deleteAllSavedPlaces()

}