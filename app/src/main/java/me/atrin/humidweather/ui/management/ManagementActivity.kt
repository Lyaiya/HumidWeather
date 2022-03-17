package me.atrin.humidweather.ui.management

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityManagementBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity
import me.atrin.humidweather.ui.place.PlaceFragment


class ManagementActivity :
    BaseBindingActivity<ActivityManagementBinding>() {

    val managementViewModel: ManagementViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setSupportActionBar(binding.managementToolbar)
        // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            showPlaceFragment()
        }

        binding.managementToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    managementViewModel.deleteAllSavedPlaces()
                    true
                }
                else -> false
            }
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