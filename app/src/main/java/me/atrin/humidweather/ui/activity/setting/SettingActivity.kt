package me.atrin.humidweather.ui.activity.setting

import android.os.Bundle
import androidx.fragment.app.commit
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivitySettingBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity

class SettingActivity : BaseBindingActivity<ActivitySettingBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.settings, SettingFragment())
            }
        }
        setSupportActionBar(binding.settingToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp()) {
            finish()
        }
        return true
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
        }
        binding.appBarLayout.addStatusBarTopPadding()
    }

}