package com.example.weatherapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherObject
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
//    val data: MutableState<DataOrException<Weather, Boolean, Exception>> = mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        loadWeather()
//    }
//
//    private fun loadWeather(){
//        getWeather("Seoul")
//    }
//
//    private fun getWeather(city: String){
//        viewModelScope.launch {
//            if(city.isEmpty()){
//                return@launch
//            }else {
//                data.value.loading = true
//                data.value = repository.getWeather(cityQuery = city)
//                Log.d("TAG", "getWeather: {${data.value.data}")
//                if(data.value.data.toString().isNotEmpty()){
//                    data.value.loading = false
//                }
//            }
//        }
//    }

    suspend fun getWeather(city: String, units: String): DataOrException<Weather, Boolean, Exception>{
        return repository.getWeather(cityQuery = city, units = units)
    }
}