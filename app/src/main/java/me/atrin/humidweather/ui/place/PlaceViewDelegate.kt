package me.atrin.humidweather.ui.place

import me.atrin.humidweather.databinding.ItemPlaceBinding
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate

class PlaceViewDelegate(private val fragment: PlaceFragment) :
    BaseBindingViewDelegate<Place, ItemPlaceBinding>() {

    override fun onBindViewHolder(
        binding: ItemPlaceBinding,
        item: Place,
        position: Int
    ) {
        val holder = BindingViewHolder(binding)

        holder.itemView.setOnClickListener {
            fragment.apply {
                mainViewModel.apply {
                    savePlace(item)
                    refresh()
                }
                // OPTIMIZE: 优雅地切换 Activity
                // startActivity<MainActivity>()
                activity?.finish()
            }
        }

        binding.apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }

    }

}