package com.nurbk.ps.countryweather.model.photos


import com.google.gson.annotations.SerializedName

data class PhotosX(
    @SerializedName("page")
    var page: Int,
    @SerializedName("pages")
    var pages: Int,
    @SerializedName("perpage")
    var perpage: Int,
    @SerializedName("photo")
    var photo: List<Photo>,
    @SerializedName("total")
    var total: String
)