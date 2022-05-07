package me.atrin.humidweather.logic.model.common

import me.atrin.humidweather.R
import me.atrin.humidweather.util.getStringByResId

class Sky(val info: String, val icon: Int, val bg: Int)

private val sky = mapOf(
    "CLEAR_DAY" to Sky(
        getStringByResId(R.string.sky_clear_info),
        R.drawable.ic_clear_day,
        R.drawable.bg_clear_day
    ),
    "CLEAR_NIGHT" to Sky(
        getStringByResId(R.string.sky_clear_info),
        R.drawable.ic_clear_night,
        R.drawable.bg_clear_night
    ),
    "PARTLY_CLOUDY_DAY" to Sky(
        getStringByResId(R.string.sky_partly_cloudy_info),
        R.drawable.ic_partly_cloud_day,
        R.drawable.bg_partly_cloudy_day
    ),
    "PARTLY_CLOUDY_NIGHT" to Sky(
        getStringByResId(R.string.sky_partly_cloudy_info),
        R.drawable.ic_partly_cloud_night,
        R.drawable.bg_partly_cloudy_night
    ),
    "CLOUDY" to Sky(
        getStringByResId(R.string.sky_cloudy_info),
        R.drawable.ic_cloudy,
        R.drawable.bg_cloudy
    ),
    "WIND" to Sky(
        getStringByResId(R.string.sky_wind_info),
        R.drawable.ic_cloudy,
        R.drawable.bg_wind
    ),
    "LIGHT_RAIN" to Sky(
        getStringByResId(R.string.sky_light_rain_info),
        R.drawable.ic_light_rain,
        R.drawable.bg_rain
    ),
    "MODERATE_RAIN" to Sky(
        getStringByResId(R.string.sky_moderate_rain_info),
        R.drawable.ic_moderate_rain,
        R.drawable.bg_rain
    ),
    "HEAVY_RAIN" to Sky(
        getStringByResId(R.string.sky_heavy_rain_info),
        R.drawable.ic_heavy_rain,
        R.drawable.bg_rain
    ),
    "STORM_RAIN" to Sky(
        getStringByResId(R.string.sky_storm_rain_info),
        R.drawable.ic_storm_rain,
        R.drawable.bg_rain
    ),
    "THUNDER_SHOWER" to Sky(
        getStringByResId(R.string.sky_thunder_shower_info),
        R.drawable.ic_thunder_shower,
        R.drawable.bg_rain
    ),
    "SLEET" to Sky(
        getStringByResId(R.string.sky_sleet_info),
        R.drawable.ic_sleet,
        R.drawable.bg_rain
    ),
    "LIGHT_SNOW" to Sky(
        getStringByResId(R.string.sky_light_snow_info),
        R.drawable.ic_light_snow,
        R.drawable.bg_snow
    ),
    "MODERATE_SNOW" to Sky(
        getStringByResId(R.string.sky_moderate_snow_info),
        R.drawable.ic_moderate_snow,
        R.drawable.bg_snow
    ),
    "HEAVY_SNOW" to Sky(
        getStringByResId(R.string.sky_heavy_snow_info),
        R.drawable.ic_heavy_snow,
        R.drawable.bg_snow
    ),
    "STORM_SNOW" to Sky(
        getStringByResId(R.string.sky_storm_snow_info),
        R.drawable.ic_heavy_snow,
        R.drawable.bg_snow
    ),
    "HAIL" to Sky(
        getStringByResId(R.string.sky_hail_info),
        R.drawable.ic_hail,
        R.drawable.bg_snow
    ),
    "LIGHT_HAZE" to Sky(
        getStringByResId(R.string.sky_light_haze_info),
        R.drawable.ic_light_haze,
        R.drawable.bg_fog
    ),
    "MODERATE_HAZE" to Sky(
        getStringByResId(R.string.sky_moderate_haze_info),
        R.drawable.ic_moderate_haze,
        R.drawable.bg_fog
    ),
    "HEAVY_HAZE" to Sky(
        getStringByResId(R.string.sky_heavy_haze_info),
        R.drawable.ic_heavy_haze,
        R.drawable.bg_fog
    ),
    "FOG" to Sky(
        getStringByResId(R.string.sky_fog_info),
        R.drawable.ic_fog,
        R.drawable.bg_fog
    ),
    "DUST" to Sky(
        getStringByResId(R.string.sky_dust_info),
        R.drawable.ic_fog,
        R.drawable.bg_fog
    )
)

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}