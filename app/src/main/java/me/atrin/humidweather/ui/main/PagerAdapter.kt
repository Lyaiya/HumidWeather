package me.atrin.humidweather.ui.main

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dylanc.longan.logDebug
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.weather.WeatherFragment


class PagerAdapter(activity: MainActivity) :
    FragmentStateAdapter(activity) {

    private val mainViewModel = activity.mainViewModel
    private var isSavedPlaceListEmpty = false

    override fun getItemCount(): Int {
        val size = mainViewModel.savedPlaceList.size

        if (size == 0) {
            isSavedPlaceListEmpty = true
            logDebug("getItemCount() returned: 0, savedPlaceList is empty")
            return 1
        }

        isSavedPlaceListEmpty = false
        logDebug("getItemCount() returned: $size")
        return size
    }

    override fun createFragment(position: Int): Fragment {
        if (isSavedPlaceListEmpty) {
            // TODO: 初次打开时的默认页
            return WeatherFragment(-1)
        }

        logDebug("createFragment() called with: position = $position")

        val place = mainViewModel.savedPlaceList.toList()[position]

        logDebug("createFragment: place = $place")

        val newFragment = WeatherFragment(position).apply {
            arguments = bundleOf(
                PlaceKey.LOCATION_LNG to place.location.lng,
                PlaceKey.LOCATION_LAT to place.location.lat,
                PlaceKey.PLACE_NAME to place.name
            )
        }
        return newFragment
    }

}