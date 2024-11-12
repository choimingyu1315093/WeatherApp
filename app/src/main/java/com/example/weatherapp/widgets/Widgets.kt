package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.util.formatDate
import com.example.weatherapp.util.formatDateTime
import com.example.weatherapp.util.formatDecimals


@Composable
fun WeatherStateImage(imageUrl: String, modifier: Modifier = Modifier){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(2000)
            .build(),
        contentDescription = "Icon Image",
        modifier = modifier
            .size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, modifier: Modifier = Modifier){
    Row (
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            modifier = modifier
                .padding(4.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.humidity),
                contentDescription = "humidity Icon",
                modifier = modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity}%",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }

        Row (
            modifier = modifier
                .padding(4.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.pressure),
                contentDescription = "pressure Icon",
                modifier = modifier.size(20.dp)
            )
            Text(
                text = "${weather.pressure} psi",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }

        Row (
            modifier = modifier
                .padding(4.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.wind),
                contentDescription = "wind Icon",
                modifier = modifier.size(20.dp)
            )
            Text(
                text = "${weather.humidity} mph",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem, modifier: Modifier = Modifier){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row {
            Image(
                painter = painterResource(R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = modifier
                    .size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunrise),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
        }

        Row {
            Text(
                text = formatDateTime(weather.sunset),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.Black
                )
            )
            Image(
                painter = painterResource(R.drawable.sunset),
                contentDescription = "sunset",
                modifier = modifier
                    .size(30.dp)
            )
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem, modifier: Modifier = Modifier) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        modifier = modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp)
        ),
        color = Color.White
    ) {
        Row (
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = formatDate(weather.dt).split(",")[0],
                modifier = modifier
                    .padding(start = 5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = modifier
                    .padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = modifier
                        .padding(4.dp),
                    style = TextStyle(
                        color = Color.Black
                    )
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = .7f),
                            fontWeight = FontWeight.SemiBold
                        ),
                    ){
                        append(formatDecimals(weather.temp.max) + "º")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray,
                        ),
                    ){
                        append(formatDecimals(weather.temp.min) + "º")
                    }
                }
            )
        }
    }
}