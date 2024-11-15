package com.example.weatherapp.widgets

import android.content.Context
import android.media.Image
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    elevation: Dp = 0.dp,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
){
    val showDialog = remember { mutableStateOf(false) }
    if(showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }
    val showIt = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card (
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(elevation),
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .padding(5.dp)
    ){
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    color = Color.Black,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )
            },
            actions = {
                if(isMainScreen){
                    IconButton(
                        onClick = {
                            onAddActionClicked.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                    IconButton(
                        onClick = {
                            showDialog.value = !showDialog.value
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "Search Icon"
                        )
                    }
                }else {
                    Box {

                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                Color.Transparent
            ),
            navigationIcon = {
                if(icon != null){
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier
                            .clickable {
                                onButtonClicked.invoke()
                            }
                    )
                }

                if(isMainScreen){
                    val isAlreadyFavList = favoriteViewModel.favList.collectAsState().value.filter { item ->
                        item.city == title.split(",")[0]
                    }
                    if(isAlreadyFavList.isNullOrEmpty()){
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            modifier = modifier
                                .scale(.9f)
                                .clickable {
                                    favoriteViewModel.insertFavorite(Favorite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    ))
                                    showIt.value = true
                                },
                            tint = Color.Red.copy(
                                alpha = .6f
                            )
                        )
                    }else {
                        Box{}
                        showIt.value = false
                    }

                    ShowToast(context = context, showIt)
                }
            },
        )
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController,
    modifier: Modifier = Modifier){

    var expanded by remember { mutableStateOf(true) }
    var items = listOf("About", "Favorites", "Settings")

    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ){
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            modifier = modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            imageVector = when(text){
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray
                        )
                    },
                    text = {
                        Text(
                            text = text,
                            modifier = modifier
                                .clickable {
                                    navController.navigate(
                                        when(text){
                                            "About" -> WeatherScreens.AboutScreen.name
                                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                                            else -> WeatherScreens.SettingsScreen.name
                                        }
                                    )
                                },
                            fontWeight = FontWeight.W300
                        )
                    },
                    onClick = {
                        expanded = false
                        showDialog.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if(showIt.value){
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
    }
}