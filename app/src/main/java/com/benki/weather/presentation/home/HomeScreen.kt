package com.benki.weather.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benki.weather.R
import com.benki.weather.network.models.WeatherApiResponse
import com.benki.weather.presentation.components.Forecast
import com.benki.weather.presentation.components.OtherDetails
import com.benki.weather.utils.DateUtils.convertDateTime

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherApiResponse: WeatherApiResponse,
) {
    val location = weatherApiResponse.location.name
    val region = weatherApiResponse.location.region
    val temperature_c = weatherApiResponse.weatherData.temperatureCelsius
    val feelsLike_c = weatherApiResponse.weatherData.feelsLikeCelsius
    val condition = weatherApiResponse.weatherData.weatherCondition.text
    val isDay = weatherApiResponse.weatherData.isDay == 1
    val dateTimeDayOfWeek = convertDateTime(weatherApiResponse.weatherData.lastUpdated)
    val day = dateTimeDayOfWeek.third
    val date = dateTimeDayOfWeek.first
    val time = dateTimeDayOfWeek.second
    val forecast = weatherApiResponse.forecast.forecastDay
    val humidity = weatherApiResponse.weatherData.humidity
    val precipitation = weatherApiResponse.weatherData.precipIN
    val wind = weatherApiResponse.weatherData.windKiloMetersPerHour

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF414345))
            ) {
                Box(
                    modifier = modifier.background(
                        brush = Brush.linearGradient(
                            colors = if (isDay) listOf(
                                Color(0xFF62CFF4),
                                Color(0xFF2C67F2)
                            ) else listOf(Color(0xFF804E9C), Color(0xFF6F99A8))
                        )
                    )
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = if (isDay) R.drawable.sun else R.drawable.moon),
                                contentDescription = null,
                                modifier = modifier.size(150.dp)
                            )
                            Spacer(modifier = modifier.height(8.dp))
                            Text(text = "Last updated at $time", color = Color.White, fontSize = 14.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$temperature_c °C",
                                color = Color.White,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = condition,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Feels like $feelsLike_c °C",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = modifier.height(16.dp))
                            Text(
                                text = day.lowercase().replaceFirstChar { it.uppercase() },
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = modifier.height(8.dp))
                            Text(
                                text = date, color = Color.White,
                            )
                            Spacer(modifier = modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = "location",
                                    tint = Color.White
                                )
                                Column {
                                    Text(
                                        text = location,
                                        color = Color.White,
                                    )
                                    Text(
                                        text = region,
                                        color = Color.White,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                OtherDetails(humidity = humidity, precipitation = precipitation, wind = wind)
                Forecast(forecastData = forecast)
            }
        }
    }
}