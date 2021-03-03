package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.cities.Cities
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface CitiesInterface {

    @Headers(
        "x-rapidapi-key: f44e63ade4msh51ced3725ee7030p10a1a4jsnd86669ee9e74"
    )
    @GET("cities/country/{city}")
    suspend fun getCityByCountry(
        @Path("city") codeCountry: String,
        @Query("pageSize") pageSize: Int = 3000, @Query("page") page: Int = 1
    ): Response<Cities>

}