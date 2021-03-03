package com.nurbk.ps.countryweather.model

import java.util.*
import javax.inject.Inject


data class ObjectDetails  @Inject constructor(var id: String, var name: String, var data: ArrayList<Any>, var type: Int)
