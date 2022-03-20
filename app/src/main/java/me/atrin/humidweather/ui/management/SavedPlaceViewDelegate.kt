package me.atrin.humidweather.ui.management

import me.atrin.humidweather.databinding.ItemSavedPlaceBinding
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate

class SavedPlaceViewDelegate :
    BaseBindingViewDelegate<Place, ItemSavedPlaceBinding>() {

    override fun onBindViewHolder(
        binding: ItemSavedPlaceBinding,
        item: Place,
        position: Int
    ) {
        binding.apply {
            // TODO: 请求天气数据
            // binding.savedPlaceIcon.setImageIcon(getSky())
            savedPlaceName.text = item.name
            // savedPlaceTemperature.text =
        }

    }

}