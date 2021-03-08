package com.nurbk.ps.countryweather.model.weather.currentweather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Main(
    @SerializedName("temp")
    var temp: Double = 0.0,

    @SerializedName("temp_min")
    var tempMin: Double = 0.0,

    @SerializedName("grnd_level")
    var grndLevel: Double = 0.0,

    @SerializedName("humidity")
    var humidity: Int = 0,

    @SerializedName("pressure")
    var pressure: Double = 0.0,

    @SerializedName("sea_level")
    var seaLevel: Double = 0.0,

    @SerializedName("temp_max")
    var tempMax: Double = 0.0,
):Parcelable