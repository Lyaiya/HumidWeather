package me.atrin.humidweather.ui.management

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.databinding.ActivityManagementBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.place.PlaceFragment

class ManagementActivity : BaseBindingActivity<ActivityManagementBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            showPlaceFragment()
        }
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

    private fun showPlaceFragment() {
        supportFragmentManager.commit {
            replace<PlaceFragment>(binding.placeFragment.id)
            addToBackStack(null)
        }
    }

}