package me.atrin.humidweather.ui.weather

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.design.snackbar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.*
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.common.getSky
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.ui.main.MainViewModel
import me.atrin.humidweather.util.ResUtil
import java.util.*

class WeatherFragment(position: Int) :
    BaseBindingFragment<FragmentWeatherBinding>() {

    private val TAG = "WeatherFragment #$position"

    val weatherViewModel: WeatherViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var containerNow: ContainerNowBinding
    private lateinit var containerHourly: ContainerHourlyBinding
    private lateinit var containerForecast: ContainerForecastBinding
    private lateinit var containerLifeIndex: ContainerLifeIndexBinding

    private lateinit var weatherSwipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mainViewModel.savedPlaceLiveData.observe(viewLifecycleOwner) { result ->
        //     weatherViewModel.placeName = result.address
        //     weatherViewModel.locationLng = result.location.lng
        //     weatherViewModel.locationLat = result.location.lat
        // }

        // if (mainViewModel.isPlaceSaved()) {
        //     mainViewModel.getSavedPlace().apply {
        //         weatherViewModel.locationLng = this.location.lng
        //         weatherViewModel.locationLat = this.location.lat
        //         weatherViewModel.placeName = this.name
        //     }
        // }

        loadWeatherData()

        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) { result ->
            val weather = result.getOrNull()

            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                snackbar("无法获取天气信息")
                result.exceptionOrNull()?.printStackTrace()
            }

            weatherSwipeRefresh.isRefreshing = false
        }

        weatherSwipeRefresh.setColorSchemeColors(
            ResUtil.getColorPrimary(
                requireContext()
            )
        )

        refreshWeather()

        weatherSwipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        // 设置 LayoutManager
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }

        // 设置 Adapter
        adapter = MultiTypeAdapter().apply {
            register(HourlyViewDelegate())
            items = weatherViewModel.hourlyList
        }
        recyclerView.adapter = adapter
    }

    override fun defineView() {
        super.defineView()

        containerNow = binding.containerNow
        containerHourly = binding.containerHourly
        containerForecast = binding.containerForecast
        containerLifeIndex = binding.containerLifeIndex

        weatherSwipeRefresh = binding.weatherSwipeRefresh
        recyclerView = containerHourly.hourlyRecyclerView
    }

    override fun initSystemBar() {
        super.initSystemBar()
        statusBar {
            transparent()
            // light = true
        }
    }

    private fun loadWeatherData() {
        requireArguments().let {
            if (it.containsKey(PlaceKey.LOCATION_LNG)) {
                val newLng = it.getString(PlaceKey.LOCATION_LNG, "")

                Log.d(TAG, "loadWeatherData: setLng")
                Log.d(
                    TAG,
                    "loadWeatherData: oldLng = ${weatherViewModel.locationLng}"
                )
                Log.d(TAG, "loadWeatherData: newLng = $newLng")

                weatherViewModel.locationLng = newLng
            }
            if (it.containsKey(PlaceKey.LOCATION_LAT)) {
                val newLat = it.getString(PlaceKey.LOCATION_LAT, "")

                Log.d(TAG, "loadWeatherData: setLat")
                Log.d(
                    TAG,
                    "loadWeatherData: oldLat = ${weatherViewModel.locationLat}"
                )
                Log.d(
                    TAG,
                    "loadWeatherData: newLat = $newLat"
                )

                weatherViewModel.locationLat = newLat
            }
            if (it.containsKey(PlaceKey.PLACE_NAME)) {
                val newPlaceName = it.getString(PlaceKey.PLACE_NAME, "")

                Log.d(TAG, "loadWeatherData: setPlaceName")
                Log.d(
                    TAG,
                    "loadWeatherData: oldPlaceName = ${weatherViewModel.placeName}"
                )
                Log.d(
                    TAG,
                    "loadWeatherData: newPlaceName = $newPlaceName"
                )

                weatherViewModel.placeName = newPlaceName
            }
        }
    }

    private fun showWeatherInfo(weather: Weather) {
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly

        // Container Now
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        containerNow.currentTemp.text = currentTempText

        val realtimeSky = getSky(realtime.skycon)
        containerNow.currentSky.text = realtimeSky.info

        val currentAQIText =
            "AQI(CN) ${realtime.airQuality.aqi.chn.toInt()}"
        containerNow.currentAQI.text = currentAQIText

        containerNow.nowContainer.setBackgroundResource(realtimeSky.bg)

        // Container Hourly
        containerHourly.hourlyDescription.text = hourly.description

        // Item Hourly
        if (weatherViewModel.hourlyList.isNotEmpty()) {
            weatherViewModel.hourlyList.clear()
        }

        val hourlyDays = hourly.skycon.size

        for (i in 0 until hourlyDays) {
            val skycon = hourly.skycon[i]
            val temperature = hourly.temperature[i]
            val nowDate = Date()

            if (skycon.datetime.before(nowDate)) {
                continue
            }

            val simpleDateFormat =
                SimpleDateFormat("a h 时", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("GMT+8:00")
                }

            val hourlyItem = HourlyItem(
                simpleDateFormat.format(skycon.datetime),
                getSky(skycon.value),
                temperature.value
            )

            weatherViewModel.hourlyList.add(hourlyItem)
        }
        adapter.notifyDataSetChanged()

        // Container Forecast
        if (containerForecast.forecastItemLayout.isNotEmpty()) {
            containerForecast.forecastItemLayout.removeAllViews()
        }

        val days = daily.skycon.size

        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]

            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.item_forecast,
                containerForecast.forecastItemLayout,
                false
            )

            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
            val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
            val temperatureInfo =
                view.findViewById<TextView>(R.id.temperatureInfo)

            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)

            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info

            val tempText =
                "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            containerForecast.forecastItemLayout.addView(view)
        }

        // Container LifeIndex
        val lifeIndex = daily.lifeIndex

        containerLifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        containerLifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        containerLifeIndex.ultravioletText.text =
            lifeIndex.ultraviolet[0].desc
        containerLifeIndex.carWashingText.text =
            lifeIndex.carWashing[0].desc
        binding.weatherScrollView.visibility = View.VISIBLE
    }

    private fun refreshWeather() {
        // OPTIMIZE: 优化刷新机制
        if (weatherViewModel.locationIsNotEmpty()) {
            Log.d(TAG, "refreshWeather: true")
            weatherViewModel.refreshWeather(
                weatherViewModel.locationLng,
                weatherViewModel.locationLat
            )
            weatherSwipeRefresh.isRefreshing = true
        } else {
            Log.d(TAG, "refreshWeather: false")
            snackbar("数据为空")
            weatherSwipeRefresh.isRefreshing = false
        }
    }

}