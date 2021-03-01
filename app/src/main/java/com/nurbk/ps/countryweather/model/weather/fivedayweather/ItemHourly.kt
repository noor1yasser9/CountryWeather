package com.nurbk.ps.countryweather.model.weather.fivedayweather

import com.google.gson.annotations.SerializedName
import com.nurbk.ps.countryweather.model.weather.common.Clouds
import com.nurbk.ps.countryweather.model.weather.common.WeatherItem
import com.nurbk.ps.countryweather.model.weather.common.Wind
import com.nurbk.ps.countryweather.model.weather.currentweather.Main
import com.nurbk.ps.countryweather.model.weather.currentweather.Sys

data class ItemHourly(
    @SerializedName("dt")
    var dt: Int = 0,
    @SerializedName("dt_txt")
    var dtTxt: String? = null,
    @SerializedName("weather")
    var weather: List<WeatherItem>? = null,
    @SerializedName("main")
    var main: Main? = null,
    @SerializedName("clouds")
    var clouds: Clouds? = null,
    @SerializedName("sys")
    var sys: Sys? = null,
    @SerializedName("wind")
    var wind: Wind? = null,
    @SerializedName("rain")
    var rain: Rain? = null,
)