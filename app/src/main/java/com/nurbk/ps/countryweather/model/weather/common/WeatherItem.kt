package com.nurbk.ps.countryweather.model.weather.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherItem(
    @SerializedName("icon")
    var icon: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("main")
    var main: String? = null,

    @SerializedName("id")
    var id: Int = 0,
):Parcelable