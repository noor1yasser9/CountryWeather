package com.nurbk.ps.countryweather.model.weather.common

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Double = 0.0,
    @SerializedName("speed")
    var speed: Double = 0.0
)