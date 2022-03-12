package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPager()
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
            light = true
        }
        // FIXME: TOOLBAR
        binding.containerToolbar.appBarLayout.addStatusBarTopPadding()
    }

}