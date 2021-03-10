package com.nurbk.ps.countryweather.utils

object ConstanceString {

    const val BASE_URL_COUNTRIES = "https://restcountries.eu/rest/v2/"
    const val BASE_URL_COUNTRIES_PAGE = "https://disease.sh/v2/"
    const val BASE_URL_CITIES = "https://geohub3.p.rapidapi.com/"
    const val BASE_URL_PHOTOS = "https://www.flickr.com/services/"
    const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY_WEATHER = "488525857914c89234b2259c86caf061"

    const val DATA_DETAILS = "dataDetails"
    const val TIME_TO_PASS = (6 * 600000).toLong()

    val WEATHER_STATUS = arrayOf(
        "Thunderstorm",
        "Drizzle",
        "Rain",
        "Snow",
        "Atmosphere",
        "Clear",
        "Few Clouds",
        "Broken Clouds",
        "Cloud"
    )

    val WEATHER_STATUS_PERSIAN = arrayOf(
        "رعد و برق",
        "نمنم باران",
        "باران",
        "برف",
        "جو ناپایدار",
        "صاف",
        "کمی ابری",
        "ابرهای پراکنده",
        "ابری"
    )

    val DAYS_OF_WEEK = arrayOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )
    val MONTH_NAME = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )


    val DAYS_OF_WEEK_PERSIAN = arrayOf(
        "یکشنبه",
        "دوشنبه",
        "سه‌شنبه",
        "چهارشنبه",
        "پنج‌شنبه",
        "جمعه",
        "شنبه"
    )
    val MONTH_NAME_PERSIAN = arrayOf(
        "فروردین",
        "اردیبهشت",
        "خرداد",
        "تیر",
        "مرداد",
        "شهریور",
        "مهر",
        "آبان",
        "آذر",
        "دی",
        "بهمن",
        "اسفند"
    )

}