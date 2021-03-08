package com.nurbk.ps.countryweather.model.weather.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(
    @SerializedName("deg")
    var deg: Double = 0.0,
    @SerializedName("speed")
    var speed: Double = 0.0
):Parcelable