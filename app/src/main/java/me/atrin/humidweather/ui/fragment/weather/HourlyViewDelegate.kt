package me.atrin.humidweather.ui.fragment.weather

import me.atrin.humidweather.databinding.ItemHourlyBinding
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate
import me.atrin.humidweather.util.getTemperatureText

class HourlyViewDelegate :
    BaseBindingViewDelegate<HourlyItem, ItemHourlyBinding>() {

    override fun onBindViewHolder(
        binding: ItemHourlyBinding,
        item: HourlyItem,
        position: Int
    ) {
        binding.apply {
            hourlyDateInfo.text = item.date
            hourlySkyIcon.setImageResource(item.skyIcon.icon)
            hourlyTemperatureInfo.text = getTemperatureText(item.temperature, true)
        }
    }

}