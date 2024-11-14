package com.example.weatherapp.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Unit
import com.example.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository): ViewModel(){
    private var _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList get() = _unitList.asStateFlow()

    init {
        getUnits()
    }

    private fun getUnits(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listOfUnits ->
                if(listOfUnits.isNullOrEmpty()){
                    Log.d("TAG", "getUnits: unit list empty")
                }else {
                    _unitList.value = listOfUnits
                }
            }
        }
    }

    fun insertUnit(unit: Unit){
        viewModelScope.launch {
            repository.insertUnit(unit)
        }
    }

    fun updateUnit(unit: Unit){
        viewModelScope.launch {
            repository.updateUnit(unit)
        }
    }

    fun deleteUnit(unit: Unit){
        viewModelScope.launch {
            repository.deleteUnit(unit)
        }
    }

    fun deleteAllUnits(){
        viewModelScope.launch {
            repository.deleteAllUnits()
        }
    }
}