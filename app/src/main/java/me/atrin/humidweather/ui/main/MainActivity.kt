package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.dylanc.longan.logDebug
import com.dylanc.longan.startActivity
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.management.ManagementActivity
import me.atrin.humidweather.ui.setting.SettingActivity
import me.atrin.humidweather.ui.weather.WeatherFragment

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    val mainViewModel: MainViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logDebug("onCreate: start")

        initViewPager()

        binding.containerToolbar.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.place_management -> {
                    startActivity<ManagementActivity>()
                    true
                }
                R.id.setting -> {
                    startActivity<SettingActivity>()
                    true
                }
                else -> false
            }
        }

        initObserver()

        logDebug("onCreate: end")
    }

    override fun onStart() {
        super.onStart()

        // 刷新已保存的地址
        refreshSavedPlaces()
    }

    private fun initViewPager() {
        logDebug("initViewPager: start")

        viewPager = binding.viewPager
        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // 页面滑动完毕后设置 Toolbar 名称
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setToolbarTitle(position)
            }
        })

        logDebug("initViewPager: end")
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
        }
        binding.containerToolbar.appBarLayout.addStatusBarTopPadding()
    }

    private fun setToolbarTitle(position: Int) {
        val fragment = supportFragmentManager
            .findFragmentByTag("f$position") as WeatherFragment

        binding.containerToolbar.toolbar.title =
            fragment.weatherViewModel.placeName
    }

    private fun initObserver() {
        // 3. 观察
        mainViewModel.savedPlacesLiveData.observe(this) { newSet ->
            if (newSet.isEmpty()) {
                logDebug("initObserver: newSet is empty")
            } else {
                newSet.forEachIndexed { index, place ->
                    logDebug("initObserver: newSet #$index = $place")
                }
            }

            mainViewModel.savedPlaceList.apply {
                forEachIndexed { index, place ->
                    logDebug("initObserver: oldSet #$index = $place")
                }

                clear()
                addAll(newSet)
                pagerAdapter.notifyDataSetChanged()

                forEachIndexed { index, place ->
                    logDebug("initObserver: finalSet #$index = $place")
                }

            }
        }
    }

    private fun refreshSavedPlaces() {
        mainViewModel.refresh()
    }

}