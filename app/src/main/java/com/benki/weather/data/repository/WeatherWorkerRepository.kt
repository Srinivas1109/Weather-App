package com.benki.weather.data.repository

import java.time.Duration

interface WeatherWorkerRepository {
    fun enqueuePeriodicWorkRequest(duration: Duration)
}