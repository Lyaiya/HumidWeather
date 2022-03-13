package me.atrin.humidweather.ui.management

import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.databinding.ActivityManagementBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity

class ManagementActivity : BaseBindingActivity<ActivityManagementBinding>() {

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
            light = true
        }
        binding.searchView.addStatusBarTopPadding()
    }

}