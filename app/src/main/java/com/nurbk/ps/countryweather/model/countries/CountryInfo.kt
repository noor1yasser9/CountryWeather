package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryInfo(
    @SerializedName("flag")
    var flag: String,
    @SerializedName("_id")
    var id: Int,
    @SerializedName("iso2")
    var iso2: String,
    @SerializedName("iso3")
    var iso3: String,
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("long")
    var long: Double,
) : Parcelable