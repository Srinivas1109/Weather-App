package com.benki.weather.network.models

import com.google.gson.annotations.SerializedName

data class ForecastDayItem(
    val date: String = "",
    @SerializedName("date_epoch")
    val dateEpoch: Long = 0L,
    val day: Day = Day()
)
