package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import me.atrin.humidweather.ui.weather.WeatherFragment

class PagerAdapter(activity: FragmentActivity, val bundle: Bundle) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        // TODO: 多个地点
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return WeatherFragment().apply {
            arguments = bundle
        }
    }

}