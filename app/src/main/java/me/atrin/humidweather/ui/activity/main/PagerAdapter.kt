package me.atrin.humidweather.ui.activity.main

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dylanc.longan.logDebug
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.fragment.weather.WeatherFragment


class PagerAdapter(mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    private val mainViewModel = mainActivity.mainViewModel

    // 可滑动的页面数量
    override fun getItemCount() = mainViewModel.savedPlaceListSize().apply {
        logDebug("getItemCount: itemCount = $this")
    }

    override fun createFragment(position: Int): Fragment {
        logDebug("createFragment: called with position = $position")

        val place = mainViewModel.savedPlaceList[position]

        logDebug("createFragment: place = $place")

        return WeatherFragment().apply {
            arguments = bundleOf(
                PlaceKey.LOCATION_LNG to place.location.lng,
                PlaceKey.LOCATION_LAT to place.location.lat,
                PlaceKey.PLACE_NAME to place.name
            )
        }
    }

}