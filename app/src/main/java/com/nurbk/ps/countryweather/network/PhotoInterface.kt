package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.photos.Photos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoInterface {

    @GET("rest/?api_key=5aa34d49b5889b2ad7b98ffd0fe03f4a&format=json&nojsoncallback=1")
   suspend fun getMethodData(
        @Query("method") method: String?,
        @Query("text") text: String?
    ): Response<Photos>

}