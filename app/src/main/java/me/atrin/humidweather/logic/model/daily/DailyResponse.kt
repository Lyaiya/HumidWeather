package me.atrin.humidweather.logic.model.daily

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class DailyResponse(val status: String, val result: Result) {

    @JsonClass(generateAdapter = true)
    data class Result(val daily: Daily)

    @JsonClass(generateAdapter = true)
    data class Daily(
        val temperature: List<Temperature>,
        val skycon: List<Skycon>,
        @Json(name = "life_index") val lifeIndex: LifeIndex,
        val astro: List<Astro>,
        val wind: List<Wind>,
        val precipitation: List<Precipitation>,
        val humidity: List<Humidity>,
        val visibility: List<Visibility>,
        val pressure: List<Pressure>
    )

    /**
     * 温度
     *
     * @param max 最大值
     * @param min 最小值
     * @param avg 平均值
     */
    @JsonClass(generateAdapter = true)
    data class Temperature(val max: Float, val min: Float, val avg: Float)

    /**
     * 天气状况
     *
     * @param value 数值
     * @param date 日期
     */
    @JsonClass(generateAdapter = true)
    data class Skycon(val value: String, val date: Date)

    /**
     * 生活指数
     *
     * @param coldRisk 感冒指数
     * @param carWashing 洗车指数
     * @param ultraviolet 紫外线指数
     * @param dressing 穿衣指数
     * @param comfort 舒适度指数
     */
    @JsonClass(generateAdapter = true)
    data class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>,
        val comfort: List<LifeDescription>
    )

    @JsonClass(generateAdapter = true)
    data class LifeDescription(val desc: String)

    /**
     * 日出与日落
     *
     * @param sunrise 日出时间
     * @param sunset 日落时间
     */
    @JsonClass(generateAdapter = true)
    data class Astro(val sunrise: AstroTime, val sunset: AstroTime)

    @JsonClass(generateAdapter = true)
    data class AstroTime(val time: String)

    /**
     * 风
     *
     * @property max 最大值
     * @property min 最小值
     * @property avg 平均值
     */
    @JsonClass(generateAdapter = true)
    data class Wind(val max: WindDetail, val min: WindDetail, val avg: WindDetail)


    /**
     * 风的详情
     *
     * @property speed 风力
     * @property direction 风向
     */
    @JsonClass(generateAdapter = true)
    data class WindDetail(val speed: Float, val direction: Float)

    /**
     * 降水强度
     *
     * @property max 最大值
     * @property min 最小值
     * @property avg 平均值
     */
    @JsonClass(generateAdapter = true)
    data class Precipitation(val max: Float, val min: Float, val avg: Float)

    /**
     * 相对湿度
     *
     * @property max 最大值
     * @property min 最小值
     * @property avg 平均值
     */
    @JsonClass(generateAdapter = true)
    data class Humidity(val max: Float, val min: Float, val avg: Float)

    /**
     * 能见度
     *
     * @property max 最大值
     * @property min 最小值
     * @property avg 平均值     */
    @JsonClass(generateAdapter = true)
    data class Visibility(val max: Float, val min: Float, val avg: Float)

    /**
     * 气压
     *
     * @property max 最大值
     * @property min 最小值
     * @property avg 平均值
     */
    @JsonClass(generateAdapter = true)
    data class Pressure(val max: Float, val min: Float, val avg: Float)

}