package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.data.WeatherDatabase
import com.example.weatherapp.model.Weather
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApi) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao {
        return weatherDatabase.getWeatherDao()
    }
}