package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.dylanc.longan.startActivity
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.management.ManagementActivity
import me.atrin.humidweather.ui.setting.SettingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    val mainViewModel: MainViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

    override fun onStart() {
        super.onStart()

        // TODO: 检查网络状况
        // if (isNetworkAvailable) {
        //
        // }
    }

    private fun initViewPager() {
        viewPager = binding.viewPager
        pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        // 页面滑动完毕后设置 Toolbar 名称
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setToolbarTitle(position)
            }
        })
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
            // light = true
        }
        binding.containerToolbar.appBarLayout.addStatusBarTopPadding()
    }

    private fun setToolbarTitle(position: Int) {
        val fragment = pagerAdapter.fragments[position]

        if (fragment != null) {
            binding.containerToolbar.toolbar.title =
                fragment.weatherViewModel.placeName
        }
    }

}