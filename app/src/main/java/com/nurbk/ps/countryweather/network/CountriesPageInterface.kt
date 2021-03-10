package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.countries.CountriesPage
import retrofit2.Response
import retrofit2.http.GET

interface CountriesPageInterface {
    @GET("countries")
    suspend fun getAllCountries(
    ): Response<CountriesPage>

}