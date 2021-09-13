package me.atrin.humidweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.logic.model.PlaceKey
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.weather.WeatherActivity

class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    companion object {
        private const val TAG = "PlaceFragment"
    }

    val viewModel by lazy {
        ViewModelProvider(this)[PlaceViewModel::class.java]
    }

    private lateinit var adapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FIXME 跳转有问题
        // Log.d(TAG, "onCreate: ")
        // if (viewModel.isPlaceSaved()) {
        //     val place = viewModel.getSavedPlace()
        //     Log.d(TAG, "onCreate: $place")
        //     val intent = Intent(context, WeatherActivity::class.java).apply {
        //         putExtra(PlaceKey.LOCATION_LNG, place.location.lng)
        //         putExtra(PlaceKey.LOCATION_LAT, place.location.lat)
        //         putExtra(PlaceKey.PLACE_NAME, place.name)
        //     }
        //     startActivity(intent)
        //     activity?.finish()
        //     return
        // }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val searchPlaceEdit = binding.searchPlaceEdit
        val bgImageView = binding.bgImageView

        val layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)

        // FIXME 替换 Adapter
        // adapter = PlaceQuickAdapter()
        //
        // adapter.setList(viewModel.placeList)

        recyclerView.adapter = adapter
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
            viewModel.placeLiveData.observe(viewLifecycleOwner, { result ->
                val places = result.getOrNull()
                if (places != null) {
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                    viewModel.placeList.clear()
                    viewModel.placeList.addAll(places)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })
        }
    }

}