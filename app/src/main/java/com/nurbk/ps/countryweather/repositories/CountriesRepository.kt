package com.nurbk.ps.countryweather.repositories

import com.nurbk.ps.countryweather.model.countries.Countries
import com.nurbk.ps.countryweather.network.CountriesInterface
import com.nurbk.ps.countryweather.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesRepository @Inject constructor(val data: CountriesInterface) {

    private val countriesMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))
    private val searchMutableLiveData: MutableStateFlow<Result<Any>> =
        MutableStateFlow(Result.empty(""))

    private lateinit var countries: Countries
    fun getAllCountries() {

        CoroutineScope(Dispatchers.IO).launch {
            countriesMutableLiveData.emit(Result.loading("loading"))
            val response = data.getAllCountries()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            countries = it
                            countriesMutableLiveData.emit(Result.success(it))
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
    }

    suspend fun searchCountries(name: String) {
        searchMutableLiveData.emit(Result.loading("loading"))
        val data = Countries()
        if (!countries.isNullOrEmpty()) {

            countries.forEach {
                if (it.name.contains(name, true)) {
                    data.add(it)
                }
            }
            searchMutableLiveData.emit(
                Result.success(
                    data,
                    "isSuccessful"
                )
            )
        } else {
            searchMutableLiveData.emit(
                Result.error(
                    "Error", Error()
                )
            )
        }
    }


    fun getCountriesLiveData(): StateFlow<Result<Any>> = countriesMutableLiveData
    fun getSearchLiveData(): StateFlow<Result<Any>> = searchMutableLiveData


}