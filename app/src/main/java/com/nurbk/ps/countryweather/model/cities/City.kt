package com.nurbk.ps.countryweather.model.cities


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("alternateNames")
    var alternateNames: List<String>,
    @SerializedName("countryCode")
    var countryCode: String,
    @SerializedName("distanceFromOrigin")
    var distanceFromOrigin: DistanceFromOrigin,
    @SerializedName("id")
    var id: Int,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("name")
    var name: String,
    @SerializedName("population")
    var population: Int,
    @SerializedName("regionCode")
    var regionCode: String,
    @SerializedName("timeZone")
    var timeZone: String,
    @SerializedName("updatedAt")
    var updatedAt: String
)