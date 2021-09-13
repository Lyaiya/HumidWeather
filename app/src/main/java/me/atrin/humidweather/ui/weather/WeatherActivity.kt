package me.atrin.humidweather.ui.weather

import android.content.res.TypedArray
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.ActivityWeatherBinding
import me.atrin.humidweather.logic.model.Weather
import me.atrin.humidweather.logic.model.getSky
import me.atrin.humidweather.ui.base.BaseBindingActivity
import java.util.*

class WeatherActivity : BaseBindingActivity<ActivityWeatherBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.swipeRefresh.isRefreshing = false
        })

        binding.swipeRefresh.setColorSchemeColors(getColorPrimary())

        refreshWeather()

        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
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
        val nowLayout = binding.includedNowLayout
        val forecastLayout = binding.includedForecastLayout
        val lifeIndexLayout = binding.includedLifeIndexLayout

        val realtime = weather.realtime
        val daily = weather.daily

        // now.xml
        nowLayout.placeName.text = viewModel.placeName

        val currentTempText = "${realtime.temperature.toInt()} ℃"
        nowLayout.currentTemp.text = currentTempText

        val realtimeSky = getSky(realtime.skycon)
        nowLayout.currentSky.text = realtimeSky.info

        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        nowLayout.currentAQI.text = currentPM25Text

        nowLayout.nowLayout.setBackgroundResource(realtimeSky.bg)

        // forecast.xml
        forecastLayout.forecastLayout.removeAllViews()

        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            // FIXME 添加一个 Adapter？
            val view = LayoutInflater.from(this).inflate(
                R.layout.forecast_item,
                forecastLayout.forecastLayout,
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
            forecastLayout.forecastLayout.addView(view)
        }

        // life_item.xml
        val lifeIndex = daily.lifeIndex

        lifeIndexLayout.coldRiskText.text = lifeIndex.coldRisk[0].desc
        lifeIndexLayout.dressingText.text = lifeIndex.dressing[0].desc
        lifeIndexLayout.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        lifeIndexLayout.carWashingText.text = lifeIndex.carWashing[0].desc
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