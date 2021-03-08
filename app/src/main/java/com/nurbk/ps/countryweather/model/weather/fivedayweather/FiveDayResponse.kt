package com.nurbk.ps.countryweather.model.weather.fivedayweather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nurbk.ps.countryweather.model.weather.daysweather.City
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FiveDayResponse(
    @SerializedName("city")
    var city: City? = null,
    @SerializedName("cnt")
    var cnt: Int = 0,
    @SerializedName("cod")
    var cod: String? = null,
    @SerializedName("message")
    var message: Double = 0.0,
    @SerializedName("list")
    var list: List<ItemHourly>? = null,
):Parcelable