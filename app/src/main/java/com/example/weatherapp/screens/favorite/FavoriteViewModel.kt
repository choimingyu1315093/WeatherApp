package com.example.weatherapp.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.repository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDBRepository): ViewModel() {
    private var _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList get() = _favList.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listOfFav ->
                if(listOfFav.isNullOrEmpty()){
                    Log.d("TAG", "getFavorites: favorite list empty")
                }else {
                    _favList.value = listOfFav
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.insertFavorite(favorite)
        }
    }

    fun updateFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.updateFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite){
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }
}