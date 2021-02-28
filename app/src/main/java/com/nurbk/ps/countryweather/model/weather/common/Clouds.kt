package com.nurbk.ps.countryweather.model.weather.common

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int = 0
)