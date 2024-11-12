package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.screens.main.MainScreen
import com.example.weatherapp.screens.main.MainViewModel
import com.example.weatherapp.screens.search.SearchScreen
import com.example.weatherapp.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        composable(
            route = WeatherScreens.MainScreen.name+"/{city}",
            arguments = listOf(navArgument(name = "city"){ type = NavType.StringType })
        ){
            MainScreen(navController = navController, city = it.arguments!!.getString("city")!!)
        }

        composable(route = WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }
    }
}