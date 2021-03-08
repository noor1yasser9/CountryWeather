package com.nurbk.ps.countryweather.model.weather.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coord(
    @SerializedName("lon")
    var lon: Double = 0.0,

    @SerializedName("lat")
    var lat: Double = 0.0,
) : Parcelable