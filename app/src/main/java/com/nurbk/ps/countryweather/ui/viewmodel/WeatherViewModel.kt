package com.nurbk.ps.countryweather.ui.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.countryweather.repositories.DetailsCountriesRepository

class WeatherViewModel @ViewModelInject constructor(
    val detailsCountriesRepository: DetailsCountriesRepository,
    application: Application,
) : AndroidViewModel(application) {

    fun getWeatherLiveData() =
        detailsCountriesRepository.getWeatherLiveData()

    fun getFiveWeatherLiveData() =
        detailsCountriesRepository.getFiveWeatherLiveData()

    fun getDaysWeatherLiveData() = detailsCountriesRepository.getDaysWeatherLiveData()


    fun getWeatherCityLiveData() = detailsCountriesRepository.getWeatherCityLiveData()
}
