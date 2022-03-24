package me.atrin.humidweather.ui.weather

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.drakeet.multitype.MultiTypeAdapter
import com.dylanc.longan.Logger
import com.dylanc.longan.design.snackbar
import com.dylanc.longan.logDebug
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.*
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.common.getSky
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.util.ResUtil
import java.util.*

class WeatherFragment(private val position: Int) :
    BaseBindingFragment<FragmentWeatherBinding>(), Logger {

    override val loggerTag: String
        get() = "${super.loggerTag} #${position}"

    val weatherViewModel: WeatherViewModel by viewModels()

    private lateinit var containerNow: ContainerNowBinding
    private lateinit var containerHourly: ContainerHourlyBinding
    private lateinit var containerForecast: ContainerForecastBinding
    private lateinit var containerLifeIndex: ContainerLifeIndexBinding

    private lateinit var weatherSwipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logDebug("onViewCreated: start")

        loadWeatherData()
        initObserver()
        initSwipeRefresh()
        initRecyclerView()
        refreshWeather()
    }

    override fun defineView() {
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
        }
    }

    private fun loadWeatherData() {
        logDebug("loadWeatherData: start")
        if (position == -1) {
            return
        }
        requireArguments().let {
            if (it.containsKey(PlaceKey.LOCATION_LNG)) {
                val newLng = it.getString(PlaceKey.LOCATION_LNG, "")

                logDebug("loadWeatherData: setLng")
                logDebug("loadWeatherData: oldLng = ${weatherViewModel.locationLng}")
                logDebug("loadWeatherData: newLng = $newLng")

                weatherViewModel.locationLng = newLng
            }
            if (it.containsKey(PlaceKey.LOCATION_LAT)) {
                val newLat = it.getString(PlaceKey.LOCATION_LAT, "")

                logDebug("loadWeatherData: setLat")
                logDebug("loadWeatherData: oldLat = ${weatherViewModel.locationLat}")
                logDebug("loadWeatherData: newLat = $newLat")

                weatherViewModel.locationLat = newLat
            }
            if (it.containsKey(PlaceKey.PLACE_NAME)) {
                val newPlaceName = it.getString(PlaceKey.PLACE_NAME, "")

                logDebug("loadWeatherData: setPlaceName")
                logDebug("loadWeatherData: oldPlaceName = ${weatherViewModel.placeName}")
                logDebug("loadWeatherData: newPlaceName = $newPlaceName")

                weatherViewModel.placeName = newPlaceName
            }
        }
    }

    private fun initObserver() {
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
    }

    private fun initSwipeRefresh() {
        weatherSwipeRefresh.setColorSchemeColors(
            ResUtil.getColorPrimary(
                requireContext()
            )
        )

        weatherSwipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
    }

    private fun initRecyclerView() {
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

    private fun showWeatherInfo(weather: Weather) {
        logDebug("showWeatherInfo: start")
        val realtime = weather.realtime
        val daily = weather.daily
        val hourly = weather.hourly

        // Container Now
        val currentTempText = "${realtime.temperature.toInt()}°C"
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
                "${temperature.min.toInt()} ~ ${temperature.max.toInt()}°C"
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
            logDebug("refreshWeather: true")
            weatherViewModel.refreshWeather(
                weatherViewModel.locationLng,
                weatherViewModel.locationLat
            )
            weatherSwipeRefresh.isRefreshing = true
        } else {
            logDebug("refreshWeather: false")
            snackbar("数据为空")
            weatherSwipeRefresh.isRefreshing = false
        }
    }

}