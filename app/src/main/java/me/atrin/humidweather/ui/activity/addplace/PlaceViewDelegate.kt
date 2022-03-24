package me.atrin.humidweather.ui.activity.addplace

import me.atrin.humidweather.databinding.ItemPlaceBinding
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate

class PlaceViewDelegate(private val addPlaceActivity: AddPlaceActivity) :
    BaseBindingViewDelegate<Place, ItemPlaceBinding>() {

    override fun onBindViewHolder(
        binding: ItemPlaceBinding,
        item: Place,
        position: Int
    ) {
        val holder = BindingViewHolder(binding)

        holder.itemView.setOnClickListener {
            addPlaceActivity.apply {
                placeViewModel.apply {
                    savePlace(item)
                }
                finish()
            }
        }

        binding.apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }
    }

}