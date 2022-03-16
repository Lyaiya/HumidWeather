package me.atrin.humidweather.ui.main

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.weather.WeatherFragment

class PagerAdapter(activity: MainActivity) :
    FragmentStateAdapter(activity) {

    companion object {
        private const val TAG = "PagerAdapter"
    }

    private val mainViewModel = activity.mainViewModel

    val fragments by lazy {
        mutableMapOf<Int, WeatherFragment>()
    }

    override fun getItemCount() = mainViewModel.places.size

    override fun createFragment(position: Int): Fragment {
        val place = mainViewModel.places[position]
        val fragment = fragments[position]

        if (fragment != null) {
            return fragment
        }

        val newFragment = WeatherFragment(position).apply {
            arguments = bundleOf(
                PlaceKey.LOCATION_LNG to place.location.lng,
                PlaceKey.LOCATION_LAT to place.location.lat,
                PlaceKey.PLACE_NAME to place.name
            )
        }
        fragments[position] = newFragment
        return newFragment
    }

}