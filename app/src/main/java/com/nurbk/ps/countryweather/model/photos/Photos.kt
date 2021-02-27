package com.nurbk.ps.countryweather.model.photos


import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("photos")
    var photos: PhotosX,
    @SerializedName("stat")
    var stat: String
)