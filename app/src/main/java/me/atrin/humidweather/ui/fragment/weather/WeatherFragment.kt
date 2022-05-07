package me.atrin.humidweather.ui.fragment.weather

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
import com.dylanc.longan.design.snackbar
import com.dylanc.longan.logDebug
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import me.atrin.humidweather.R
import me.atrin.humidweather.databinding.*
import me.atrin.humidweather.logic.model.common.Weather
import me.atrin.humidweather.logic.model.common.getSky
import me.atrin.humidweather.logic.model.daily.DailyResponse
import me.atrin.humidweather.logic.model.hourly.HourlyItem
import me.atrin.humidweather.logic.model.hourly.HourlyResponse
import me.atrin.humidweather.logic.model.place.PlaceKey
import me.atrin.humidweather.logic.model.realtime.RealtimeResponse
import me.atrin.humidweather.ui.base.BaseBindingFragment
import me.atrin.humidweather.util.getColorPrimary
import me.atrin.humidweather.util.getTemperatureText
import me.atrin.humidweather.util.temperatureUnitText
import java.time.format.DateTimeFormatter

class WeatherFragment : BaseBindingFragment<FragmentWeatherBinding>() {

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
        val args = requireArguments()

        if (args.containsKey(PlaceKey.LOCATION_LNG)) {
            val newLng = args.getString(PlaceKey.LOCATION_LNG, "")

            logDebug("loadWeatherData: setLng")
            logDebug("loadWeatherData: oldLng = ${weatherViewModel.locationLng}")
            logDebug("loadWeatherData: newLng = $newLng")

            weatherViewModel.locationLng = newLng
        }
        if (args.containsKey(PlaceKey.LOCATION_LAT)) {
            val newLat = args.getString(PlaceKey.LOCATION_LAT, "")

            logDebug("loadWeatherData: setLat")
            logDebug("loadWeatherData: oldLat = ${weatherViewModel.locationLat}")
            logDebug("loadWeatherData: newLat = $newLat")

            weatherViewModel.locationLat = newLat
        }
        if (args.containsKey(PlaceKey.PLACE_NAME)) {
            val newPlaceName = args.getString(PlaceKey.PLACE_NAME, "")

            logDebug("loadWeatherData: setPlaceName")
            logDebug("loadWeatherData: oldPlaceName = ${weatherViewModel.placeName}")
            logDebug("loadWeatherData: newPlaceName = $newPlaceName")

            weatherViewModel.placeName = newPlaceName
        }
    }

    private fun initObserver() {
        weatherViewModel.weatherLiveData.observe(viewLifecycleOwner) { result ->
            val weather = result.getOrNull()

            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                snackbar(getString(R.string.snack_no_weather))
                result.exceptionOrNull()?.printStackTrace()
            }

            weatherSwipeRefresh.isRefreshing = false
        }
    }

    private fun initSwipeRefresh() {
        weatherSwipeRefresh.setColorSchemeColors(getColorPrimary())

        weatherSwipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
    }

    private fun initRecyclerView() {
        // 设置 LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
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
        showNowContainer(realtime)

        // Container Hourly
        showHourlyContainer(hourly)

        // Container Forecast
        showForecastContainer(daily)

        // Container LifeIndex
        showLifeIndexContainer(daily)

        binding.weatherScrollView.visibility = View.VISIBLE
    }

    private fun showNowContainer(realtime: RealtimeResponse.Realtime) {
        val currentTempText = getTemperatureText(realtime.temperature, true)
        containerNow.currentTemp.text = currentTempText

        val realtimeSky = getSky(realtime.skycon)
        containerNow.currentSky.text = realtimeSky.info

        val currentAQIText = "AQI(CN) ${realtime.airQuality.aqi.chn.toInt()}"
        containerNow.currentAQI.text = currentAQIText

        containerNow.nowContainer.setBackgroundResource(realtimeSky.bg)
    }

    private fun showHourlyContainer(hourly: HourlyResponse.Hourly) {
        containerHourly.hourlyDescription.text = hourly.description

        if (weatherViewModel.hourlyList.isNotEmpty()) {
            weatherViewModel.hourlyList.clear()
        }

        val hourlyDays = hourly.skycon.size

        for (index in 0 until hourlyDays) {
            val skycon = hourly.skycon[index]
            val temperature = hourly.temperature[index]

            val hourlyItem = HourlyItem(skycon.dateTime, getSky(skycon.value), temperature.value)

            weatherViewModel.hourlyList.add(hourlyItem)
        }
        adapter.notifyDataSetChanged()
    }

    private fun showForecastContainer(daily: DailyResponse.Daily) {
        if (containerForecast.forecastItemLayout.isNotEmpty()) {
            containerForecast.forecastItemLayout.removeAllViews()
        }

        val days = daily.skycon.size

        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        containerForecast.forecastTitle.text = getString(R.string.forecast_title).format(days)

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
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            val sky = getSky(skycon.value)

            dateInfo.text = skycon.dateTime.format(dateTimeFormatter)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info

            val minTemperatureText = getTemperatureText(temperature.min, false)
            val maxTemperatureText = getTemperatureText(temperature.max, false)

            val temperatureText =
                "$minTemperatureText ~ $maxTemperatureText${temperatureUnitText}"
            temperatureInfo.text = temperatureText
            containerForecast.forecastItemLayout.addView(view)
        }
    }

    private fun showLifeIndexContainer(daily: DailyResponse.Daily) {
        val lifeIndex = daily.lifeIndex

        containerLifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        containerLifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        containerLifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        containerLifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
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
            snackbar(getString(R.string.snack_empty_data))
            weatherSwipeRefresh.isRefreshing = false
        }
    }

}