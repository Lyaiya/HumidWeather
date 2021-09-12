package me.atrin.humidweather.ui.place

import me.atrin.humidweather.databinding.PlaceItemBinding
import me.atrin.humidweather.logic.model.Place
import me.atrin.humidweather.ui.base.BaseBindingQuickAdapter

class PlaceQuickAdapter(private val placeList: List<Place>) : BaseBindingQuickAdapter<Place, PlaceItemBinding>() {

    override fun convert(holder: BaseBindingHolder, item: Place) {
        holder.getViewBinding<PlaceItemBinding>().apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }
    }

}