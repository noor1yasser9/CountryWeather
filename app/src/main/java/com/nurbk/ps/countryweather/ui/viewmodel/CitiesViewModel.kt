package com.nurbk.ps.countryweather.ui.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.countryweather.repositories.DetailsCountriesRepository


class CitiesViewModel @ViewModelInject constructor(
    val detailsCountriesRepository: DetailsCountriesRepository,
    application: Application
) : AndroidViewModel(application) {

    fun getCitiesLiveData() = detailsCountriesRepository.getCitiesLiveData()

    fun getWeatherLiveData() =
        detailsCountriesRepository.getWeatherLiveData()

    fun getPhotosLiveData() = detailsCountriesRepository.getPhotosLiveData()
    fun getCountryNameLiveData()= detailsCountriesRepository.getCountryNameLiveData()


}