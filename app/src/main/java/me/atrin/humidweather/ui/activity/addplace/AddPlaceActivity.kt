package me.atrin.humidweather.ui.activity.addplace

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.context
import com.dylanc.longan.design.snackbar
import com.dylanc.longan.lifecycleOwner
import com.dylanc.longan.logDebug
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityAddplaceBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.util.ResUtil

class AddPlaceActivity : BaseBindingActivity<ActivityAddplaceBinding>() {

    val placeViewModel: PlaceViewModel by viewModels()

    private lateinit var adapter: MultiTypeAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var bgImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        initSearchBar()
        initObserver()
    }

    override fun defineView() {
        super.defineView()

        recyclerView = binding.recyclerView
        bgImageView = binding.bgImageView
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
        }
        binding.actionBarLayout.addStatusBarTopPadding()
    }

    private fun initRecyclerView() {
        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 设置 Adapter
        adapter = MultiTypeAdapter().apply {
            register(PlaceViewDelegate(this@AddPlaceActivity))
            items = placeViewModel.searchResultPlaceList
        }
        recyclerView.adapter = adapter
    }

    private fun initSearchBar() {
        // 搜索框文本监听
        binding.searchBar.addTextChangedListener { text ->
            placeViewModel.setPlaceName(text.toString())
        }
    }

    private fun initObserver() {
        placeViewModel.placeNameLiveData.observe(lifecycleOwner) { placeName: String ->
            logDebug("onViewCreated: placeName = $placeName")
            if (placeName.isBlank()) {
                showSearchedPlaces(false)

                placeViewModel.searchResultPlaceList.clear()
                adapter.notifyDataSetChanged()
            } else {
                // OPTIMIZE: 延迟响应数据
                placeViewModel.searchPlaces(placeName)
            }
        }

        // 3. 观察到改动
        placeViewModel.placeLiveData.observe(lifecycleOwner) { result ->
            val places = result.getOrNull()

            if (places != null) {
                showSearchedPlaces(true)

                placeViewModel.searchResultPlaceList.clear()
                placeViewModel.searchResultPlaceList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                snackbar(ResUtil.getStringByResId(R.string.snack_no_place))
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