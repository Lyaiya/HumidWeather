package me.atrin.humidweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import me.atrin.humidweather.MainActivity
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.weather.WeatherActivity

class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    val viewModel by lazy {
        ViewModelProvider(this)[PlaceViewModel::class.java]
    }

    private lateinit var adapter: MultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveToWeatherActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBar()

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
                    Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            }
        }
    }

    private fun moveToWeatherActivity() {
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra(PlaceKey.LOCATION_LNG, place.location.lng)
                putExtra(PlaceKey.LOCATION_LAT, place.location.lat)
                putExtra(PlaceKey.PLACE_NAME, place.name)
            }
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun initBar() {
        binding.actionBarLayout.addStatusBarTopPadding()
    }

}