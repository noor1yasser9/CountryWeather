package com.nurbk.ps.countryweather.model.photos


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("farm")
    var farm: Int,
    @SerializedName("id")
    var id: String,
    @SerializedName("isfamily")
    var isfamily: Int,
    @SerializedName("isfriend")
    var isfriend: Int,
    @SerializedName("ispublic")
    var ispublic: Int,
    @SerializedName("owner")
    var owner: String,
    @SerializedName("secret")
    var secret: String,
    @SerializedName("server")
    var server: String,
    @SerializedName("title")
    var title: String
)