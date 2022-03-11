package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.place.PlaceViewModel
import me.atrin.humidweather.ui.weather.WeatherFragment

class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val weatherFragment = WeatherFragment().apply {
            val place = PlaceViewModel().getSavedPlace()
            arguments = Bundle().apply {
                putString(PlaceKey.LOCATION_LNG, place.location.lng)
                putString(PlaceKey.LOCATION_LAT, place.location.lat)
                putString(PlaceKey.PLACE_NAME, place.name)
            }
        }
        return weatherFragment
    }
}