package com.nurbk.ps.countryweather.model.cities


import com.google.gson.annotations.SerializedName

data class Cities(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("metaData")
    var metaData: MetaData,
    @SerializedName("paging")
    var paging: Paging
)