package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency(
    @SerializedName("code")
    var code: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("symbol")
    var symbol: String
) : Parcelable