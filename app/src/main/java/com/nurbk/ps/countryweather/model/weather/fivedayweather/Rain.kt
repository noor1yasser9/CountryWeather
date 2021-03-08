package com.nurbk.ps.countryweather.model.weather.fivedayweather

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rain(
    @SerializedName("3h")
    var jsonMember3h: Double = 0.0
):Parcelable