package com.nurbk.ps.countryweather.model.weather.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clouds(
    @SerializedName("all")
    var all: Int = 0
):Parcelable