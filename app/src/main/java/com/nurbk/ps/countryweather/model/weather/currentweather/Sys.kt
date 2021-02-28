package com.nurbk.ps.countryweather.model.weather.currentweather

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    var country: String? = null,

    @SerializedName("sunrise")
    var sunrise: Double = 0.0,

    @SerializedName("sunset")
    var sunset: Double = 0.0,

    @SerializedName("message")
    var message: Double = 0.0,
)