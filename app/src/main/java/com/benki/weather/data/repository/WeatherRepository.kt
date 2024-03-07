package com.benki.weather.data.repository

import android.location.Location
import com.benki.weather.network.models.NetworkResponse
import com.benki.weather.network.models.WeatherApiResponse
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {
    val networkResponse: StateFlow<NetworkResponse>
    suspend fun getWeatherUpdates(
        query: String,
        airQualityIndex: String
    )

    suspend fun getDeviceLastLocation() : Location?

    suspend fun getWorkerWeatherUpdates(
        query: String,
        airQualityIndex: String
    ): WeatherApiResponse?
}