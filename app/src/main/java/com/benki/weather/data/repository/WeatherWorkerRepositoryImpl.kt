package com.benki.weather.data.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.benki.weather.workers.WeatherWorker
import java.time.Duration

class WeatherWorkerRepositoryImpl(private val context: Context) : WeatherWorkerRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun enqueuePeriodicWorkRequest(duration: Duration) {
        val periodicWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<WeatherWorker>(duration)
//                .setInputData(workDataOf("location" to location))
                .setConstraints(Constraints(requiresBatteryNotLow = true))
                .build()

        workManager.enqueueUniquePeriodicWork(
            WEATHER_WORKER_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest
        )
    }

    companion object {
        const val WEATHER_WORKER_NAME = "com.benki.weather.weather-worker"
    }
}