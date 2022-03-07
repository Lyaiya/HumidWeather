package me.atrin.humidweather.ui.weather

import me.atrin.humidweather.databinding.HourlyItemBinding
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate

class HourlyViewDelegate : BaseBindingViewDelegate<HourlyItem, HourlyItemBinding>() {

    override fun onBindViewHolder(
        binding: HourlyItemBinding,
        item: HourlyItem,
        position: Int
    ) {
        binding.apply {
            hourlyDateInfo.text = item.date
            hourlySkyIcon.setBackgroundResource(item.skyIcon.icon)
            hourlyTemperatureInfo.text = "${item.temperature} ℃"
        }
    }

}