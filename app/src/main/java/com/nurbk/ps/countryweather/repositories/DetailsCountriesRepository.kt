package com.nurbk.ps.countryweather.repositories

import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.network.CitiesInterface
import com.nurbk.ps.countryweather.network.CountriesInterface
import com.nurbk.ps.countryweather.network.PhotoInterface
import com.nurbk.ps.countryweather.network.WeatherInterface
import com.nurbk.ps.countryweather.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsCountriesRepository @Inject constructor(
    val countriesInterface: CountriesInterface,
    val data: CitiesInterface,
    val photos: PhotoInterface,
    val weather: WeatherInterface,
) {

    private val countriesMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val countriesNameMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val photosMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val weatherMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val fiveWeatherMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val daysWeatherLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))

    private val photosObject: ObjectDetails =
        ObjectDetails(UUID.randomUUID().toString(), "Photos", ArrayList(), 1)

    private val citiesObject: ObjectDetails =
        ObjectDetails(UUID.randomUUID().toString(), "Cities", ArrayList(), 2)


    fun getAllCitiesByCountries(codeCountry: String, nameCountry: String) {
        getWeather(nameCountry)
        citiesObject.data.clear()
        CoroutineScope(Dispatchers.IO).launch {
            countriesMutableLiveData.emit(Result.loading("loading"))
            val response = data.getCityByCountry(codeCountry = codeCountry)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            citiesObject.data.addAll(it.data.cities)
                            countriesMutableLiveData.emit(
                                Result.success(
                                    citiesObject
                                )
                            )
                        }

                    } else {
                        countriesMutableLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    countriesMutableLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    countriesMutableLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
        getPhotos(nameCountry)
    }


    private fun getPhotos(countryName: String) {
        photosObject.data.clear()
        CoroutineScope(Dispatchers.IO).launch {
            photosMutableLiveData.emit(Result.loading("Loading"))
            val response =
                photos.getMethodData(method = "flickr.photos.search", text = "City $countryName")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            photosObject.data.addAll(it.photos.photo)
                            photosMutableLiveData.emit(Result.success(photosObject))
                        }

                    } else {
                        photosMutableLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    photosMutableLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    photosMutableLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }


    fun getWeather(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            weatherMutableLiveData.emit(Result.loading("Loading"))
            val response = weather.getCurrentWeather(q = query)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            weatherMutableLiveData.emit(Result.success(it))
                            getFiveWeather(query)
                        }

                    } else {
                        weatherMutableLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    weatherMutableLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    weatherMutableLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    private fun getFiveWeather(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            fiveWeatherMutableLiveData.emit(Result.loading("Loading"))
            val response = weather.getFiveDaysWeather(q = query)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            fiveWeatherMutableLiveData.emit(Result.success(it))
                        }
                        getDayWeather(query)
                    } else {
                        fiveWeatherMutableLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    fiveWeatherMutableLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    fiveWeatherMutableLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    private fun getDayWeather(query: String) {

        CoroutineScope(Dispatchers.IO).launch {
            daysWeatherLiveData.emit(Result.loading("Loading"))
            val response = weather.getMultipleDaysWeather(q = query)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            daysWeatherLiveData.emit(Result.success(it))
                        }

                    } else {
                        daysWeatherLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    daysWeatherLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    daysWeatherLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }


    fun getNameCountries(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            countriesMutableLiveData.emit(Result.loading("loading"))
            val response = countriesInterface.getCountriesName(name)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            countriesNameMutableLiveData.emit(Result.success(it[0]))
                        }

                    } else {
                        countriesNameMutableLiveData.emit(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    countriesNameMutableLiveData.emit(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    countriesNameMutableLiveData.emit(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }

    fun getCitiesLiveData(): StateFlow<Result<Any>> = countriesMutableLiveData
    fun getCountryNameLiveData(): StateFlow<Result<Any>> = countriesNameMutableLiveData
    fun getWeatherLiveData(): StateFlow<Result<Any>> = weatherMutableLiveData
    fun getFiveWeatherLiveData(): StateFlow<Result<Any>> = fiveWeatherMutableLiveData
    fun getPhotosLiveData(): StateFlow<Result<Any>> = photosMutableLiveData
    fun getDaysWeatherLiveData(): StateFlow<Result<Any>> = daysWeatherLiveData


}