package me.atrin.humidweather.util

import android.graphics.Color
import com.dylanc.longan.application
import com.dylanc.longan.context

object ResUtil {

    fun getColorPrimary(): Int {
        val obtainStyledAttributes =
            application.context.obtainStyledAttributes(intArrayOf(android.R.attr.colorPrimary))
        val color = obtainStyledAttributes.getColor(0, Color.BLACK)
        obtainStyledAttributes.recycle()
        return color
    }

    fun getStringByResId(resId: Int) =
        application.context.getString(resId)

    fun getIntByResId(resId: Int) =
        application.context.resources.getInteger(resId)

    fun getStringArrayByResId(resId: Int): Array<String> =
        application.context.resources.getStringArray(resId)

}