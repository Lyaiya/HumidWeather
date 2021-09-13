package me.atrin.humidweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import me.atrin.humidweather.MainActivity
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
        Log.d(TAG, "onCreate: start")
        moveToWeatherActivity()
        Log.d(TAG, "onCreate: finish")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: start")

        initBar()

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

        Log.d(TAG, "onViewCreated: finish")
    }

    private fun moveToWeatherActivity() {
        Log.d(TAG, "moveToWeatherActivity: start")

        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            Log.d(TAG, "moveToWeatherActivity: $place")
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra(PlaceKey.LOCATION_LNG, place.location.lng)
                putExtra(PlaceKey.LOCATION_LAT, place.location.lat)
                putExtra(PlaceKey.PLACE_NAME, place.name)
            }
            Log.d(TAG, "moveToWeatherActivity: startActivity")
            startActivity(intent)
            Log.d(TAG, "moveToWeatherActivity: finishActivity")
            activity?.finish()
        }

        Log.d(TAG, "moveToWeatherActivity: finish")
    }

    protected fun initBar() {
        binding.actionBarLayout.addStatusBarTopPadding()
    }

}