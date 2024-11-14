package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>>{
        return weatherDao.getFavorites()
    }

    suspend fun insertFavorite(favorite: Favorite){
        weatherDao.insertFavorite(favorite)
    }

    suspend fun updateFavorite(favorite: Favorite){
        weatherDao.updateFavorite(favorite)
    }

    suspend fun deleteAllFavorites(){
        weatherDao.deleteAllFavorites()
    }

    suspend fun deleteFavorite(favorite: Favorite){
        weatherDao.deleteFavorite(favorite)
    }

    suspend fun getFavById(city: String): Favorite{
        return weatherDao.getFavById(city)
    }

    //----------------------------------------------------------------

    fun getUnits(): Flow<List<Unit>>{
        return weatherDao.getUnits()
    }

    suspend fun insertUnit(unit: Unit){
        weatherDao.insertUnit(unit)
    }

    suspend fun updateUnit(unit: Unit){
        weatherDao.updateUnit(unit)
    }

    suspend fun deleteAllUnits(){
        weatherDao.deleteAllUnits()
    }

    suspend fun deleteUnit(unit: Unit){
        weatherDao.deleteUnit(unit)
    }
}