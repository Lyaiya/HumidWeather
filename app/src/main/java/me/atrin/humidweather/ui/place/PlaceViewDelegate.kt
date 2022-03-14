package me.atrin.humidweather.ui.place

import androidx.core.os.bundleOf
import me.atrin.humidweather.databinding.ItemPlaceBinding
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate
import me.atrin.humidweather.ui.main.MainActivity

class PlaceViewDelegate(private val fragment: PlaceFragment) :
    BaseBindingViewDelegate<Place, ItemPlaceBinding>() {

    override fun onBindViewHolder(binding: ItemPlaceBinding, item: Place, position: Int) {
        val holder = BindingViewHolder(binding)

        holder.itemView.setOnClickListener {
            fragment.viewModel.savePlace(item)

            MainActivity.start(
                bundleOf(
                    PlaceKey.LOCATION_LNG to item.location.lng,
                    PlaceKey.LOCATION_LAT to item.location.lat,
                    PlaceKey.PLACE_NAME to item.name
                )
            )
            fragment.activity?.finish()
        }

        binding.apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }

    }

}