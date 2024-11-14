package com.example.weatherapp.screens.favorite

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.search.SearchBar
import com.example.weatherapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    Scaffold (
        topBar = {
            WeatherAppBar(
                title = "Favorite Cities",
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
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = viewModel.favList.collectAsState().value
                LazyColumn {
                    items(list){
                        CityRow(favorite = it, navController = navController, favoriteViewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel,
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name+"/${favorite.city}")
            },
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp)
        ),
        color = Color(0xFFB2DFDB)
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = favorite.city,
                modifier = modifier
                    .padding(start = 4.dp)
            )
            Surface(
                modifier = modifier
                    .padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favorite.country,
                    modifier = modifier
                        .padding(4.dp)
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Icon",
                modifier = modifier
                    .clickable {
                        favoriteViewModel.deleteFavorite(favorite)
                    },
                tint = Color.Red.copy(
                    alpha = .3f
                )
            )
        }
    }
}