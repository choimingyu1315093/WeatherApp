package com.example.weatherapp.data

import com.example.weatherapp.model.WeatherObject

data class DataOrException<T, Boolean, E: Exception> (
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)