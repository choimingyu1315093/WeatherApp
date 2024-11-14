package com.example.weatherapp.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier){
    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "About",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                elevation = 5.dp,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ){ innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Weather App v0.1",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}