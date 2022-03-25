package me.atrin.humidweather.util

import android.content.Context
import android.graphics.Color
import me.atrin.humidweather.HumidWeatherApplication

object ResUtil {

    fun getColorPrimary(context: Context): Int {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
        val color = obtainStyledAttributes.getColor(0, Color.BLACK)
        obtainStyledAttributes.recycle()
        return color
    }

    fun getStringByResId(resId: Int) =
        HumidWeatherApplication.context.getString(resId)

    fun getIntByResId(resId: Int) =
        HumidWeatherApplication.context.resources.getInteger(resId)

    fun getStringArrayByResId(resId: Int): Array<String> =
        HumidWeatherApplication.context.resources.getStringArray(resId)


}