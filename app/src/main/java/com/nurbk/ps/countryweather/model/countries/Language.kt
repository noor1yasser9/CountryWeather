package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language(
    @SerializedName("iso639_1")
    var iso6391: String,
    @SerializedName("iso639_2")
    var iso6392: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("nativeName")
    var nativeName: String
) : Parcelable