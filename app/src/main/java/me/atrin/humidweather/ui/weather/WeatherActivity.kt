package me.atrin.humidweather.ui.weather

import android.content.Context
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.*
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.common.getSky
import me.atrin.humidweather.ui.base.BaseBindingActivity
import java.util.*

class WeatherActivity : BaseBindingActivity<ActivityWeatherBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    private lateinit var inclNowLayout: NowBinding
    private lateinit var inclHourlyLayout: HourlyBinding
    private lateinit var inclForecastLayout: ForecastBinding
    private lateinit var inclLifeIndexLayout: LifeIndexBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inclNowLayout = binding.includedNowLayout
        inclHourlyLayout = binding.includedHourlyLayout
        inclForecastLayout = binding.includedForecastLayout
        inclLifeIndexLayout = binding.includedLifeIndexLayout

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        binding.swipeRefresh.setColorSchemeColors(getColorPrimary())

        refreshWeather()

        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        // 点击按钮打开抽屉
        inclNowLayout.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        // 监听抽屉状态
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                // 抽屉关闭时隐藏输入法
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    override fun initBar() {
        super.initBar()
        statusBar {
            // 布局是否侵入状态栏
            fitWindow = true
            // 状态栏透明
            transparent()
        }
        // 给 titleLayout 增加状态栏高度
        binding.includedNowLayout.titleLayout.addStatusBarTopPadding()
    }

    private fun showWeatherInfo(weather: Weather) {
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly

        // now.xml
        inclNowLayout.placeName.text = viewModel.placeName

        val currentTempText = "${realtime.temperature.toInt()} ℃"
        inclNowLayout.currentTemp.text = currentTempText

        val realtimeSky = getSky(realtime.skycon)
        inclNowLayout.currentSky.text = realtimeSky.info

        val currentAQIText = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        inclNowLayout.currentAQI.text = currentAQIText

        inclNowLayout.nowLayout.setBackgroundResource(realtimeSky.bg)

        // TODO: hourly.xml
        inclHourlyLayout.hourlyDescription.text = hourly.description

        // TODO: hourly_item.xml

        // forecast.xml
        inclForecastLayout.forecastLayout.removeAllViews()

        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                inclForecastLayout.forecastLayout,
                false
            )

            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)

            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info

            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            inclForecastLayout.forecastLayout.addView(view)
        }

        // life_item.xml
        val lifeIndex = daily.lifeIndex

        inclLifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        inclLifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        inclLifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        inclLifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.weatherLayout.visibility = View.VISIBLE
    }

    private fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun getColorPrimary(): Int {
        val obtainStyledAttributes = obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
        val color = obtainStyledAttributes.getColor(0, Color.BLACK)
        obtainStyledAttributes.recycle()
        return color
    }

}