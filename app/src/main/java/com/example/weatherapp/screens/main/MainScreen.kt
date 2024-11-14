package com.example.weatherapp.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.settings.SettingsViewModel
import com.example.weatherapp.util.formatDate
import com.example.weatherapp.util.formatDateTime
import com.example.weatherapp.util.formatDecimals
import com.example.weatherapp.widgets.HumidityWindPressureRow
import com.example.weatherapp.widgets.SunsetSunriseRow
import com.example.weatherapp.widgets.WeatherAppBar
import com.example.weatherapp.widgets.WeatherDetailRow
import com.example.weatherapp.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    city: String = "Seoul",
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val curCity: String = if(city.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember { mutableStateOf("imperial") }
    var isImperial by remember { mutableStateOf(false) }

    if(!unitFromDb.isNullOrEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(initialValue = DataOrException(loading = true)){
            value = mainViewModel.getWeather(curCity, unit)
        }.value

        if(weatherData.loading == true){
            CircularProgressIndicator()
        }else if(weatherData.data != null){
            MainScaffold(weatherData.data!!, navController)
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, modifier: Modifier = Modifier){
    Scaffold (
        topBar = {
            WeatherAppBar(
                title = weather.city.name + ",${weather.city.country}",
                navController = navController,
                elevation = 5.dp,
                onButtonClicked = {
                    Log.d("TAG", "MainScaffold: Back Click")
                },
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }
    ){ innerPadding ->
        MainContent(data = weather, paddingValues = innerPadding)
    }
}

@Composable
fun MainContent(data: Weather, paddingValues: PaddingValues, modifier: Modifier = Modifier){
    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Surface(
        modifier = modifier
            .padding(paddingValues)
    ) {
        Column (
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = formatDate(data.list[0].dt),
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                modifier = modifier
                    .padding(6.dp)
            )

            Surface(
                modifier = modifier
                    .padding(4.dp)
                    .size(200.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    WeatherStateImage(imageUrl = imageUrl)
                    Text(
                        text = formatDecimals(data.list[0].temp.day) + "º",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = data.list[0].weather[0].main,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic
                        )
                    )
                }
            }

            HumidityWindPressureRow(weather = data.list[0])
            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .height(.5.dp)
                    .background(Color.LightGray)
            )
            SunsetSunriseRow(weather = data.list[0])
            Text(
                text = "This Week",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Surface(
                modifier = modifier
                    .fillMaxSize(),
                color = Color(0xFFEEF1EF),
                shape = RoundedCornerShape(size = 14.dp)
            ) {
                LazyColumn (
                    modifier = modifier
                        .padding(2.dp),
                    contentPadding = PaddingValues(1.dp)
                ){
                    items(items = data.list){ item ->
                        WeatherDetailRow(weather = item)
                    }
                }
            }
        }
    }
}
