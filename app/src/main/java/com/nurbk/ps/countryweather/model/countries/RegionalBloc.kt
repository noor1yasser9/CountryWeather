package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RegionalBloc(
    @SerializedName("acronym")
    var acronym: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("otherAcronyms")
    var otherAcronyms: List<@RawValue Any>,
    @SerializedName("otherNames")
    var otherNames: List<String>
) : Parcelable