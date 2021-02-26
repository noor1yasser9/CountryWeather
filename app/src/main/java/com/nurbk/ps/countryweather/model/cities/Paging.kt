package com.nurbk.ps.countryweather.model.cities


import com.google.gson.annotations.SerializedName

data class Paging(
    @SerializedName("nextPageLink")
    var nextPageLink: String,
    @SerializedName("previousPageLink")
    var previousPageLink: String
)