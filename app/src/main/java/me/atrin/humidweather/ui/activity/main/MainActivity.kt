package me.atrin.humidweather.ui.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.dylanc.longan.lifecycleOwner
import com.dylanc.longan.logDebug
import com.dylanc.longan.startActivity
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.activity.addplace.AddPlaceActivity
import me.atrin.humidweather.ui.activity.setting.SettingActivity
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.fragment.weather.WeatherFragment

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    val mainViewModel by viewModels<MainViewModel>()

    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logDebug("onCreate: start")

        initViewPager()
        initToolbar()
        initObserver()
    }

    override fun onStart() {
        super.onStart()

        // 刷新已保存的地址
        refreshSavedPlaceList()
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
        }
        binding.containerToolbar.appBarLayout.addStatusBarTopPadding()
    }

    private fun initViewPager() {
        viewPager = binding.viewPager
        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // 页面滑动完毕后设置 Toolbar 名称
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setToolbarTitle(position)
            }
        })
    }

    private fun initToolbar() {
        binding.containerToolbar.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_place -> {
                    startActivity<AddPlaceActivity>()
                    true
                }
                R.id.delete_place -> {
                    deletePlace()
                    true
                }
                R.id.setting -> {
                    startActivity<SettingActivity>()
                    true
                }
                else -> false
            }
        }
    }

    private fun setToolbarTitle(position: Int) {
        val fragment = supportFragmentManager.findFragmentByTag("f$position") as WeatherFragment

        binding.containerToolbar.toolbar.title = fragment.weatherViewModel.placeName
    }

    private fun initObserver() {
        // 3. 观察
        mainViewModel.savedPlaceListLiveData.observe(lifecycleOwner) { newList ->
            if (newList.isEmpty()) {
                logDebug("initObserver: newList is empty")
            } else {
                newList.forEachIndexed { index, place ->
                    logDebug("initObserver: newList #$index = $place")
                }
            }

            mainViewModel.savedPlaceList.apply {
                if (isEmpty()) {
                    logDebug("initObserver: oldList is empty")
                } else {
                    forEachIndexed { index, place ->
                        logDebug("initObserver: oldList #$index = $place")
                    }
                }

                // OPTIMIZE: 或许还有优化的余地
                clear()
                addAll(newList)
                pagerAdapter.notifyDataSetChanged()

                forEachIndexed { index, place ->
                    logDebug("initObserver: finalList #$index = $place")
                }
            }
        }
    }

    private fun refreshSavedPlaceList() {
        logDebug("refreshSavedPlaceList: start")
        mainViewModel.refresh()
    }

    private fun deletePlace() {
        mainViewModel.deletePlaceByPosition(viewPager.currentItem)
        refreshSavedPlaceList()
    }

}