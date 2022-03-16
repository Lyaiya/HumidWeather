package me.atrin.humidweather.ui.place

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.design.snackbar
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.main.MainViewModel

class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    companion object {
        private const val TAG = "PlaceFragment"
    }

    val mainViewModel: MainViewModel by activityViewModels()
    val placeViewModel: PlaceViewModel by viewModels()

    private lateinit var adapter: MultiTypeAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var bgImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // moveToWeatherActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // 设置 Adapter
        adapter = MultiTypeAdapter().apply {
            register(PlaceViewDelegate(this@PlaceFragment))
            items = placeViewModel.placeList
        }
        recyclerView.adapter = adapter

        // 搜索框文本监听
        binding.searchBar.addTextChangedListener { text ->
            placeViewModel.setPlaceName(text.toString())
        }

        createObserver()
    }

    override fun defineView() {
        super.defineView()

        recyclerView = binding.recyclerView
        bgImageView = binding.bgImageView
    }

    private fun createObserver() {
        placeViewModel.placeNameLiveData.observe(viewLifecycleOwner) { placeName: String ->
            Log.d(TAG, "onViewCreated: \"$placeName\"")
            if (placeName.isBlank()) {
                showPlaces(false)

                placeViewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            } else {
                // TODO: 延迟响应数据
                placeViewModel.searchPlaces(placeName)
            }
        }

        placeViewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()

            if (places != null) {
                showPlaces(true)

                placeViewModel.placeList.clear()
                placeViewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                snackbar("未能查询到任何地点")
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    private fun showPlaces(boolean: Boolean) {
        if (boolean) {
            recyclerView.visibility = View.VISIBLE
            bgImageView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            bgImageView.visibility = View.VISIBLE
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