package com.nurbk.ps.countryweather.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nurbk.ps.countryweather.repositories.CountriesRepository
import com.nurbk.ps.countryweather.repositories.DetailsCountriesRepository
import com.nurbk.ps.countryweather.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    val countriesRepository: CountriesRepository,
    val detailsCountriesRepository: DetailsCountriesRepository,
    application: Application,
) : AndroidViewModel(application) {

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        countriesRepository.getAllCountries()
    }

    fun searchCountries(name: String) {
        viewModelScope.launch {
            countriesRepository.searchCountries(name)
        }
    }

    private fun getAllCitiesByCountries(nameCountries: String, nameCountry: String) {
        detailsCountriesRepository.getAllCitiesByCountries(nameCountries, nameCountry)
    }

    fun getNameCountries(code: String, name: String) {
        detailsCountriesRepository.getNameCountries(name)
        getAllCitiesByCountries(code, name)
    }


    fun getWeather(nameCountry: String) = detailsCountriesRepository.getWeather(nameCountry)

    fun getCountriesLiveData(): StateFlow<Result<Any>> = countriesRepository.getCountriesLiveData()
    fun getSearchLiveData(): StateFlow<Result<Any>> = countriesRepository.getSearchLiveData()

}