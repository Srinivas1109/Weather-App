package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class AirQuality(
    val co: Double = 0.0,
    val no2: Double = 0.0,
    val o3: Double = 0.0,
    val so2: Double = 0.0,
    @SerializedName("pm2_5")
    val pm25: Double = 0.0,
    val pm10: Double = 0.0,
    @SerializedName("us-epa-index")
    val usEpaIndex: Double = 0.0,
    @SerializedName("gb-defra-index")
    val gbDefraIndex: Double = 0.0
)
