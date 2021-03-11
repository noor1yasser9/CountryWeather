package com.nurbk.ps.countryweather.ui.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.countryweather.repositories.DetailsCountriesRepository
import kotlinx.coroutines.launch


class CitiesViewModel @ViewModelInject constructor(
    val detailsCountriesRepository: DetailsCountriesRepository,
    application: Application
) : AndroidViewModel(application) {

    fun getCitiesLiveData() = detailsCountriesRepository.getCitiesLiveData()
    fun getCitiesSearchLiveData() = detailsCountriesRepository.getCitiesSearchLiveData()

    fun getWeatherLiveData() =
        detailsCountriesRepository.getWeatherLiveData()

    fun getPhotosLiveData() = detailsCountriesRepository.getPhotosLiveData()
    fun getCountryNameLiveData()= detailsCountriesRepository.getCountryNameLiveData()

    fun searchCities(name: String) {
        viewModelScope.launch {
            detailsCountriesRepository.searchCities(name)
        }
    }
}