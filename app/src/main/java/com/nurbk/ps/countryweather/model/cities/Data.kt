package com.nurbk.ps.countryweather.model.cities


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cities")
    var cities: List<City>
)