package me.atrin.humidweather.ui.weather

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.*
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.common.getSky
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.util.ResUtil
import java.util.*

class WeatherFragment : BaseBindingFragment<FragmentWeatherBinding>() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var containerNow: ContainerNowBinding
    private lateinit var containerHourly: ContainerHourlyBinding
    private lateinit var containerForecast: ContainerForecastBinding
    private lateinit var containerLifeIndex: ContainerLifeIndexBinding

    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = arguments?.getString("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = arguments?.getString("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = arguments?.getString("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(requireContext(), "无法获取天气信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.weatherSwipeRefresh.isRefreshing = false
        }

        binding.weatherSwipeRefresh.setColorSchemeColors(ResUtil.getColorPrimary(requireContext()))

        refreshWeather()

        binding.weatherSwipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        // // 点击按钮打开抽屉
        // inclNowLayout.navBtn.setOnClickListener {
        //     binding.drawerLayout.openDrawer(GravityCompat.START)
        // }
        //
        // // 监听抽屉状态
        // binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
        //     override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
        //
        //     override fun onDrawerOpened(drawerView: View) {}
        //
        //     override fun onDrawerClosed(drawerView: View) {
        //         val manager = getSystemService(Context.INPUT_METHOD_SERVICE)
        //                 as InputMethodManager
        //         // 抽屉关闭时隐藏输入法
        //         manager.hideSoftInputFromWindow(
        //             drawerView.windowToken,
        //             InputMethodManager.HIDE_NOT_ALWAYS
        //         )
        //     }
        //
        //     override fun onDrawerStateChanged(newState: Int) {}
        // })

        val recyclerView = containerHourly.hourlyRecyclerView

        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        // 设置 Adapter
        adapter = MultiTypeAdapter()
        adapter.register(HourlyViewDelegate())
        adapter.items = viewModel.hourlyList

        recyclerView.adapter = adapter
    }

    override fun defineView() {
        super.defineView()

        containerNow = binding.containerNow
        containerHourly = binding.containerHourly
        containerForecast = binding.containerForecast
        containerLifeIndex = binding.containerLifeIndex
    }

    override fun initSystemBar() {
        super.initSystemBar()
        // 给 titleLayout 增加状态栏高度
        containerNow.titleLayout.addStatusBarTopPadding()
    }

    private fun showWeatherInfo(weather: Weather) {
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly

        // now.xml
        containerNow.placeName.text = viewModel.placeName

        val currentTempText = "${realtime.temperature.toInt()} ℃"
        containerNow.currentTemp.text = currentTempText

        val realtimeSky = getSky(realtime.skycon)
        containerNow.currentSky.text = realtimeSky.info

        val currentAQIText = "AQI(CN) ${realtime.airQuality.aqi.chn.toInt()}"
        containerNow.currentAQI.text = currentAQIText

        containerNow.nowLayout.setBackgroundResource(realtimeSky.bg)

        // hourly.xml
        containerHourly.hourlyDescription.text = hourly.description

        // hourly_item.xml
        if (viewModel.hourlyList.isNotEmpty()) {
            viewModel.hourlyList.clear()
        }

        val hourlyDays = hourly.skycon.size

        for (i in 0 until hourlyDays) {
            val skycon = hourly.skycon[i]
            val temperature = hourly.temperature[i]
            val nowDate = Date()

            if (skycon.datetime.before(nowDate)) {
                continue
            }

            val simpleDateFormat = SimpleDateFormat("a h 时", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("GMT+8:00")
            }

            val hourlyItem = HourlyItem(
                simpleDateFormat.format(skycon.datetime),
                getSky(skycon.value),
                temperature.value
            )

            viewModel.hourlyList.add(hourlyItem)
        }
        adapter.notifyDataSetChanged()

        // forecast.xml
        if (containerForecast.forecastLayout.isNotEmpty()) {
            containerForecast.forecastLayout.removeAllViews()
        }

        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.item_forecast,
                containerForecast.forecastLayout,
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
            containerForecast.forecastLayout.addView(view)
        }

        // life_item.xml
        val lifeIndex = daily.lifeIndex

        containerLifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        containerLifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        containerLifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        containerLifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.weatherScrollView.visibility = View.VISIBLE
    }

    private fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        binding.weatherSwipeRefresh.isRefreshing = true
    }

}