package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.countries.Countries
import retrofit2.Response
import retrofit2.http.GET

interface CountriesInterface {


    @GET("all")
     suspend fun getAllCountries(
    ): Response<Countries>


}