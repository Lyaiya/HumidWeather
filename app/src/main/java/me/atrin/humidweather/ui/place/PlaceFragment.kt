package me.atrin.humidweather.ui.place

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.design.snackbar
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.main.MainViewModel

class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    val placeViewModel: PlaceViewModel by viewModels()
    val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: MultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // moveToWeatherActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val searchPlaceEdit = binding.searchPlaceEdit
        val bgImageView = binding.bgImageView

        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // 设置 Adapter
        adapter = MultiTypeAdapter().apply {
            register(PlaceViewDelegate(this@PlaceFragment))
            items = placeViewModel.placeList
        }
        recyclerView.adapter = adapter

        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()

            if (content.isNotEmpty()) {
                placeViewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE

                placeViewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }

            placeViewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
                val places = result.getOrNull()

                if (places != null) {
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE

                    placeViewModel.placeList.clear()
                    placeViewModel.placeList.addAll(places)
                    adapter.notifyDataSetChanged()
                } else {
                    snackbar("未能查询到任何地点")
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        }
    }

    // private fun moveToWeatherActivity() {
    //     if (activity is MainActivity && viewModel.isPlaceSaved()) {
    //         val place = viewModel.getSavedPlace()
    //         val intent = Intent(context, WeatherActivity::class.java).apply {
    //             putExtra(PlaceKey.LOCATION_LNG, place.location.lng)
    //             putExtra(PlaceKey.LOCATION_LAT, place.location.lat)
    //             putExtra(PlaceKey.PLACE_NAME, place.name)
    //         }
    //         startActivity(intent)
    //         activity?.finish()
    //     }
    // }

}