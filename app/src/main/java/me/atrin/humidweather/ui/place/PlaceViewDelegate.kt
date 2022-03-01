package me.atrin.humidweather.ui.place

import android.content.Intent
import me.atrin.humidweather.databinding.PlaceItemBinding
import me.atrin.humidweather.logic.model.PlaceKey
import me.atrin.humidweather.logic.model.response.Place
import me.atrin.humidweather.ui.base.BaseBindingViewDelegate
import me.atrin.humidweather.ui.weather.WeatherActivity

class PlaceViewDelegate(private val fragment: PlaceFragment) :
    BaseBindingViewDelegate<Place, PlaceItemBinding>() {

    override fun onBindViewHolder(binding: PlaceItemBinding, item: Place, position: Int) {
        val holder = BindingViewHolder(binding)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WeatherActivity::class.java).apply {
                putExtra(PlaceKey.LOCATION_LNG, item.location.lng)
                putExtra(PlaceKey.LOCATION_LAT, item.location.lat)
                putExtra(PlaceKey.PLACE_NAME, item.name)
            }
            fragment.viewModel.savePlace(item)
            fragment.startActivity(intent)
            fragment.activity?.finish()
        }

        holder.binding.apply {
            placeName.text = item.name
            placeAddress.text = item.address
        }

    }

}