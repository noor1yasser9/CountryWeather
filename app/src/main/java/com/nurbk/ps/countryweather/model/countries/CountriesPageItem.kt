package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountriesPageItem(
    @SerializedName("active")
    var active: Double,
    @SerializedName("activePerOneMillion")
    var activePerOneMillion: Double,
    @SerializedName("cases")
    var cases: Double,
    @SerializedName("casesPerOneMillion")
    var casesPerOneMillion: Double,
    @SerializedName("continent")
    var continent: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("countryInfo")
    var countryInfo: CountryInfo,
    @SerializedName("critical")
    var critical: Double,
    @SerializedName("criticalPerOneMillion")
    var criticalPerOneMillion: Double,
    @SerializedName("deaths")
    var deaths: Double,
    @SerializedName("deathsPerOneMillion")
    var deathsPerOneMillion: Double,
    @SerializedName("oneCasePerPeople")
    var oneCasePerPeople: Double,
    @SerializedName("oneDeathPerPeople")
    var oneDeathPerPeople: Double,
    @SerializedName("oneTestPerPeople")
    var oneTestPerPeople: Double,
    @SerializedName("population")
    var population: Double,
    @SerializedName("recovered")
    var recovered: Double,
    @SerializedName("recoveredPerOneMillion")
    var recoveredPerOneMillion: Double,
    @SerializedName("tests")
    var tests: Int,
    @SerializedName("testsPerOneMillion")
    var testsPerOneMillion: Double,
    @SerializedName("todayCases")
    var todayCases: Double,
    @SerializedName("todayDeaths")
    var todayDeaths: Double,
    @SerializedName("todayRecovered")
    var todayRecovered: Double,
    @SerializedName("updated")
    var updated: Double
):Parcelable