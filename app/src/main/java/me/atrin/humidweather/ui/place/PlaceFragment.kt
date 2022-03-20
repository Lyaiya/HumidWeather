package me.atrin.humidweather.ui.place

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.design.snackbar
import com.dylanc.longan.logDebug
import me.atrin.humidweather.databinding.FragmentPlaceBinding
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.main.MainViewModel


class PlaceFragment : BaseBindingFragment<FragmentPlaceBinding>() {

    companion object {
        private const val TAG = "PlaceFragment"
    }

    val mainViewModel by activityViewModels<MainViewModel>()
    val placeViewModel by viewModels<PlaceViewModel>()

    private lateinit var adapter: MultiTypeAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var bgImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // 设置 Adapter
        adapter = MultiTypeAdapter().apply {
            register(PlaceViewDelegate(this@PlaceFragment))
            items = placeViewModel.searchedPlaceList
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
            logDebug("onViewCreated: placeName = $placeName")
            if (placeName.isBlank()) {
                showSearchedPlaces(false)

                placeViewModel.searchedPlaceList.clear()
                adapter.notifyDataSetChanged()
            } else {
                // OPTIMIZE: 延迟响应数据
                placeViewModel.searchPlaces(placeName)
            }
        }

        // 3. 观察到改动
        placeViewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()

            if (places != null) {
                showSearchedPlaces(true)

                placeViewModel.searchedPlaceList.clear()
                placeViewModel.searchedPlaceList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                snackbar("未能查询到任何地点")
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }

    private fun showSearchedPlaces(boolean: Boolean) {
        if (boolean) {
            recyclerView.visibility = View.VISIBLE
            bgImageView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            bgImageView.visibility = View.VISIBLE
        }
    }

}