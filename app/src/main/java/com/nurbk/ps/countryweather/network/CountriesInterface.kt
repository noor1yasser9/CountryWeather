package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.countries.Countries
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesInterface {


    @GET("name/{name}")
    suspend fun getCountriesName(
        @Path("name") name: String,
    ): Response<Countries>


}