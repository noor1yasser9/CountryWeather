package com.nurbk.ps.countryweather.model.countries


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountriesItem(
    @SerializedName("alpha2Code")
    var alpha2Code: String,
    @SerializedName("alpha3Code")
    var alpha3Code: String,
    @SerializedName("altSpellings")
    var altSpellings: List<String>,
    @SerializedName("area")
    var area: Double,
    @SerializedName("borders")
    var borders: List<String>,
    @SerializedName("callingCodes")
    var callingCodes: List<String>,
    @SerializedName("capital")
    var capital: String,
    @SerializedName("cioc")
    var cioc: String,
    @SerializedName("currencies")
    var currencies: List<Currency>,
    @SerializedName("demonym")
    var demonym: String,
    @SerializedName("flag")
    var flag: String,
    @SerializedName("gini")
    var gini: Double,
    @SerializedName("languages")
    var languages: List<Language>,
    @SerializedName("latlng")
    var latlng: List<Double>,
    @SerializedName("name")
    var name: String,
    @SerializedName("nativeName")
    var nativeName: String,
    @SerializedName("numericCode")
    var numericCode: String,
    @SerializedName("population")
    var population: Int,
    @SerializedName("region")
    var region: String,
    @SerializedName("regionalBlocs")
    var regionalBlocs: List<RegionalBloc>,
    @SerializedName("subregion")
    var subregion: String,
    @SerializedName("timezones")
    var timezones: List<String>,
    @SerializedName("topLevelDomain")
    var topLevelDomain: List<String>,
    @SerializedName("translations")
    var translations: Translations
) : Parcelable