package me.atrin.humidweather.ui.main

import android.os.Bundle
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

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()

        binding.containerToolbar.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.management -> {
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
        val pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
            // light = true
        }
        binding.containerToolbar.appBarLayout.addStatusBarTopPadding()
    }

}