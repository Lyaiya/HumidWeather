package me.atrin.humidweather.ui.activity.management

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.activity
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityManagementBinding
import me.atrin.humidweather.logic.model.place.Location
import me.atrin.humidweather.logic.model.place.Place
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.fragment.place.PlaceFragment

class ManagementActivity :
    BaseBindingActivity<ActivityManagementBinding>() {

    val managementViewModel: ManagementViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 更好的返回上级
        // setSupportActionBar(binding.managementToolbar)
        // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initFab()
        initToolbar()
        loadSavedPlaceData()
        initRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp()) {
            finish()
        }
        return true
    }

    override fun defineView() {
        recyclerView = binding.savedPlaceRecyclerView
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
        }
        binding.appBarLayout.addStatusBarTopPadding()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = MultiTypeAdapter().apply {
            register(SavedPlaceViewDelegate())
            items = managementViewModel.savedPlaceList
        }

        recyclerView.adapter = adapter
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            showPlaceFragment()
        }
    }

    private fun initToolbar() {
        binding.managementToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    managementViewModel.clearSavedPlaceList()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadSavedPlaceData() {
        // TEST: 测试数据
        managementViewModel.savedPlaceList.add(
            Place(
                "珠海市",
                Location("", ""),
                ""
            )
        )
    }

    private fun showPlaceFragment() {
        // TODO: 地址添加需要进一步优化
        supportFragmentManager.commit {
            replace<PlaceFragment>(binding.placeFragment.id)
        }
        binding.fab.hide()
    }

    private fun showSavedPlace() {

    }

}