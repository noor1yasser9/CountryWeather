package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Translations(
    @SerializedName("br")
    var br: String,
    @SerializedName("de")
    var de: String,
    @SerializedName("es")
    var es: String,
    @SerializedName("fa")
    var fa: String,
    @SerializedName("fr")
    var fr: String,
    @SerializedName("hr")
    var hr: String,
    @SerializedName("it")
    var `it`: String,
    @SerializedName("ja")
    var ja: String,
    @SerializedName("nl")
    var nl: String,
    @SerializedName("pt")
    var pt: String
):Parcelable