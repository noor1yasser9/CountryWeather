package com.nurbk.ps.countryweather.model.weather.daysweather

import com.google.gson.annotations.SerializedName
import com.nurbk.ps.countryweather.model.weather.common.Coord

data class City(
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("coord")
    var coord: Coord? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("population")
    var population: Int = 0,
)