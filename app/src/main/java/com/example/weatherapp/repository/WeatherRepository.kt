package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi){
    private val dataOrException = DataOrException<Weather, Boolean, Exception>()

    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception>{
        try {
            dataOrException.loading = true
            dataOrException.data = api.getWeather(query = cityQuery)
            if(dataOrException.data.toString().isNotEmpty()){
                dataOrException.loading = false
            }
        }catch (exception: Exception){
            dataOrException.e = exception
            Log.d("TAG", "getWeather: error ${dataOrException.e?.localizedMessage}")
        }
        return dataOrException
    }
}