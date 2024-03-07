package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avgtemp_c")
    val avgTemperature: Double = 0.0,
    val condition: WeatherCondition = WeatherCondition()
)
