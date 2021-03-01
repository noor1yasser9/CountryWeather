package com.nurbk.ps.countryweather.model.weather.daysweather

import com.google.gson.annotations.SerializedName
import com.nurbk.ps.countryweather.model.weather.common.WeatherItem

data class ListItem(
    @SerializedName("dt")
    var dt: Int = 0,
    @SerializedName("temp")
    var temp: Temp? = null,
    @SerializedName("deg")
    var deg: Int = 0,
    @SerializedName("weather")
    var weather: List<WeatherItem>? = null,
    @SerializedName("humidity")
    var humidity: Int = 0,
    @SerializedName("pressure")
    var pressure: Double = 0.0,
    @SerializedName("clouds")
    var clouds: Int = 0,
    @SerializedName("speed")
    var speed: Double = 0.0,
    @SerializedName("rain")
    var rain: Double = 0.0
)