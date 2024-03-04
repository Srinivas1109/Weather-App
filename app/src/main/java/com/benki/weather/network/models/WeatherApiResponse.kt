package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
    val location: WeatherLocation = WeatherLocation(),
    @SerializedName("current")
    val weatherData: WeatherData = WeatherData()
)
