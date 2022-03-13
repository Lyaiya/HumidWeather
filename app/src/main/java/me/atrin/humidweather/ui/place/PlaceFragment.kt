package me.atrin.humidweather.ui.place

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.design.snackbar
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.ui.base.BaseBindingFragment

class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    val viewModel: PlaceViewModel by activityViewModels()

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
        adapter = MultiTypeAdapter()
        adapter.register(PlaceViewDelegate(this))
        adapter.items = viewModel.placeList

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
            viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
                val places = result.getOrNull()
                if (places != null) {
                    recyclerView.visibility = View.VISIBLE
                    bgImageView.visibility = View.GONE
                    viewModel.placeList.clear()
                    viewModel.placeList.addAll(places)
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

    override fun initSystemBar() {
        binding.actionBarLayout.addStatusBarTopPadding()
    }

}