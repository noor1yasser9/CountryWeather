package com.nurbk.ps.countryweather.model.weather.common

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @SerializedName("icon")
    var icon: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("main")
    var main: String? = null,

    @SerializedName("id")
    var id: Int = 0,
)