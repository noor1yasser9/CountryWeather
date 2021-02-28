package com.nurbk.ps.countryweather.model.weather.currentweather

import com.google.gson.annotations.SerializedName
import com.nurbk.ps.countryweather.model.weather.common.Clouds
import com.nurbk.ps.countryweather.model.weather.common.Coord
import com.nurbk.ps.countryweather.model.weather.common.WeatherItem
import com.nurbk.ps.countryweather.model.weather.common.Wind

data class CurrentWeatherResponse(
    @SerializedName("dt")
    var dt: Double = 0.0,

    @SerializedName("coord")
    var coord: Coord? = null,

    @SerializedName("weather")
    var weather: List<WeatherItem>? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("cod")
    var cod :Int= 0,

    @SerializedName("main")
    var main: Main? = null,

    @SerializedName("clouds")
    var clouds: Clouds? = null,

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("sys")
    var sys: Sys? = null,

    @SerializedName("base")
    var base: String? = null,

    @SerializedName("wind")
    var wind: Wind? = null,
)