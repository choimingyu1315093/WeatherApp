package com.example.weatherapp.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Unit
import com.example.weatherapp.screens.favorite.CityRow
import com.example.weatherapp.screens.favorite.FavoriteViewModel
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    val unitToggleState = remember { mutableStateOf(false) }
    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = viewModel.unitList.collectAsState().value
    val choiceDefaulted = if(choiceFromDb.isNullOrEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    val choiceState = remember { mutableStateOf(choiceDefaulted) }

    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController,
                elevation = 5.dp,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    ){innerPadding ->
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
                    text = "Change Units of Measurement",
                    modifier = modifier
                        .padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState.value,
                    onCheckedChange = {
                        unitToggleState.value = !it
                        choiceState.value = if(unitToggleState.value) "Imperial (F)" else "Metric (C)"
                    },
                    modifier = modifier
                        .fillMaxWidth(.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = .4f))
                ) {
                    Text(
                        text = if(unitToggleState.value) "Fahrenheit ºF" else "Celsius ºC"
                    )
                }

                Button(
                    onClick = {
                        viewModel.deleteAllUnits()
                        viewModel.insertUnit(Unit(choiceState.value))
                    },
                    modifier = modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        text = "Save",
                        modifier = modifier
                            .padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}