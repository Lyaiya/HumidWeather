package me.atrin.humidweather

import android.os.Bundle
import android.util.Log
import me.atrin.humidweather.databinding.ActivityMainBinding
import me.atrin.humidweather.ui.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: start")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: start")
    }

}