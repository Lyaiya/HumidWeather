package me.atrin.humidweather.ui.place

import me.atrin.humidweather.databinding.ItemPlaceBinding
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate

class PlaceViewDelegate(private val fragment: PlaceFragment) :
    BaseBindingViewDelegate<Place, ItemPlaceBinding>() {

    override fun onBindViewHolder(binding: ItemPlaceBinding, item: Place, position: Int) {
        val holder = BindingViewHolder(binding)

        holder.itemView.setOnClickListener {
            // val intent = Intent(holder.itemView.context, WeatherActivity::class.java).apply {
            //     putExtra(PlaceKey.LOCATION_LNG, item.location.lng)
            //     putExtra(PlaceKey.LOCATION_LAT, item.location.lat)
            //     putExtra(PlaceKey.PLACE_NAME, item.name)
            // }
            // fragment.viewModel.savePlace(item)
            // fragment.startActivity(intent)
            // fragment.activity?.finish()
        }

        binding.apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }

    }

}