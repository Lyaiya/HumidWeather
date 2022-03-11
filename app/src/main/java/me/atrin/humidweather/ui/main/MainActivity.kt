package me.atrin.humidweather.ui.main

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPager = binding.viewPager
        val pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            // 布局是否侵入状态栏
            fitWindow = true
            // 状态栏透明
            transparent()
        }
        // FIXME: TOOLBAR
        // binding.containerToolbar.toolbar.addStatusBarTopPadding()
    }

}