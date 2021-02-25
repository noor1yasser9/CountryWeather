package com.nurbk.ps.countryweather.di

import com.nurbk.ps.countryweather.network.CitiesInterface
import com.nurbk.ps.countryweather.network.CountriesInterface
import com.nurbk.ps.countryweather.network.PhotoInterface
import com.nurbk.ps.countryweather.network.WeatherInterface
import com.nurbk.ps.countryweather.utils.ConstanceString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun InstaceRetrofit(baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun countriesInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_COUNTRIES).create(CountriesInterface::class.java)

    @Provides
    @Singleton
    fun citiesInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_CITIES).create(CitiesInterface::class.java)

    @Provides
    @Singleton
    fun photosInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_PHOTOS).create(PhotoInterface::class.java)

    @Provides
    @Singleton
    fun weatherInterface() =
        InstaceRetrofit(ConstanceString.BASE_URL_WEATHER).create(WeatherInterface::class.java)



}