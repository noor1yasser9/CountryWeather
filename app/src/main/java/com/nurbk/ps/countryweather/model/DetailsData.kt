package com.nurbk.ps.countryweather.model

import java.util.*
import javax.inject.Inject


data class DetailsData @Inject constructor(val id: String, var dataList: ArrayList<ObjectDetails>)