package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class WeatherLocation(
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val localtime: String = "",
    @SerializedName("tz_id")
    val tzId: String = "",
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long = 0L
)
