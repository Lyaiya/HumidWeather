package me.atrin.humidweather.util

import android.content.Context
import android.graphics.Color

object ResUtil {

    fun getColorPrimary(context: Context): Int {
        val obtainStyledAttributes =
            context.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
        val color = obtainStyledAttributes.getColor(0, Color.BLACK)
        obtainStyledAttributes.recycle()
        return color
    }

}