package com.benki.weather.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.benki.weather.data.repository.WeatherRepository
import com.benki.weather.network.models.NetworkResponse
import com.benki.weather.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val weatherRepository: WeatherRepository
) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val location = weatherRepository.getDeviceLastLocation()
            println("WEATHER_WORKER Location: $location")
            if (location != null) {
                val weatherResponseApi = weatherRepository.getWorkerWeatherUpdates(
                    "${location.latitude},${location.longitude}",
                    "yes"
                )
                if (weatherResponseApi != null) {
                    val weatherLocation = weatherResponseApi.location.name
                    val temp_c = weatherResponseApi.weatherData.temperatureCelsius
                    val condition = weatherResponseApi.weatherData.weatherCondition.text
                    val notification = NotificationHelper.createNotification(
                        context = context,
                        channelId = "com.benki.weather.weather-updates",
                        title = weatherLocation,
                        description = "$temp_c $condition"
                    )
                    NotificationHelper.sendNotification(context, 200, notification)
                }
                println("WEATHER_WORKER: $weatherResponseApi")
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}