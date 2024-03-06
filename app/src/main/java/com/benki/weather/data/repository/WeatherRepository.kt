package com.benki.weather.data.repository

import android.location.Location
import com.benki.weather.network.models.NetworkResponse
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {
    val networkResponse: StateFlow<NetworkResponse>
    suspend fun getWeatherUpdates(
        query: String,
        airQualityIndex: String
    )

    suspend fun getDeviceLastLocation() : Location?
}