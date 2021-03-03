package com.nurbk.ps.countryweather.network

import com.nurbk.ps.countryweather.model.weather.currentweather.CurrentWeatherResponse
import com.nurbk.ps.countryweather.model.weather.daysweather.MultipleDaysWeatherResponse
import com.nurbk.ps.countryweather.model.weather.fivedayweather.FiveDayResponse
import com.nurbk.ps.countryweather.utils.ConstanceString.API_KEY_WEATHER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {


    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String?,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String = API_KEY_WEATHER
    ): Response<CurrentWeatherResponse>


    @GET("forecast")
    suspend fun getFiveDaysWeather(
        @Query("q") q: String?,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String = API_KEY_WEATHER
    ): Response<FiveDayResponse>


    @GET("forecast/daily")
    suspend fun getMultipleDaysWeather(
        @Query("q") q: String,
        @Query("cnt") dayCount: Int = 16,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String = API_KEY_WEATHER
    ): Response<MultipleDaysWeatherResponse>
}